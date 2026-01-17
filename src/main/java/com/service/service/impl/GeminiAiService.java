package com.service.service.impl;

import com.service.dto.GradingResult;
import com.service.service.AiService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeminiAiService implements AiService {

    private final ChatClient chatClient;

    @Value("${app.ai.fallback-model:gemini-flash-latest}")
    private String fallbackModel;

    // Tên "geminiGrading" phải khớp với cấu hình trong yaml
    @Override
    @CircuitBreaker(name = "geminiGrading", fallbackMethod = "fallbackEvaluateAnswer")
    public GradingResult evaluateAnswer(String questTitle, String questDescription, String userAnswer) {
        return callAiService(questTitle, questDescription, userAnswer, null);
    }

    public GradingResult fallbackEvaluateAnswer(String questTitle, String questDescription, String userAnswer, Throwable t) {
        log.warn("Circuit Breaker triggered or Exception caught: {}. Switching to FALLBACK model: {}", t.getMessage(), fallbackModel);

        GoogleGenAiChatOptions fallbackOptions = GoogleGenAiChatOptions.builder()
                .model(fallbackModel)
                .build();

        return callAiService(questTitle, questDescription, userAnswer, fallbackOptions);
    }

    private GradingResult callAiService(String questTitle, String questDescription, String userAnswer, GoogleGenAiChatOptions options) {
        String systemText = """
                Role: Strict academic grader.
                Rule: Score > 90% implies isCorrect=true.
                Output: JSON.
                """;

        String userText = """
                Question: {quest_title}
                Description: {quest_description}
                Answer: {user_answer}
                """;

        var request = chatClient.prompt()
                .system(systemText)
                .user(u -> u.text(userText)
                        .param("quest_title", questTitle)
                        .param("quest_description", questDescription)
                        .param("user_answer", userAnswer)
                );

        if (options != null) {
            request.options(options);
        }

        return request.call().entity(GradingResult.class);
    }
}