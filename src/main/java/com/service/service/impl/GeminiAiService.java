package com.service.service.impl;

import com.service.dto.GradingResult;
import com.service.dto.entity.QuestDTO;
import com.service.service.AiService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeminiAiService implements AiService {

    private final ChatClient chatClient;

    @Value("${app.ai.fallback-model:gemini-flash-latest}")
    private String fallbackModel;

    // Tên "geminiGrading" phải khớp với cấu hình trong yaml
    @Override
    public GradingResult evaluateAnswer(String questTitle, String questDescription, String userAnswer) {
        try {
            String systemText = """
                    Role: Strict academic grader.
                    Rule: Score > 90% implies isCorrect=true.
                    Output: A JSON Array of QuestDTO objects.
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

            return callAiService(request, new ParameterizedTypeReference<>() {
            });
        } catch (Exception e) {
            log.error("callAiService failed {}", e.getMessage());
            throw e;
        }
    }

    @Override
    @CircuitBreaker(name = "geminiResilience", fallbackMethod = "fallbackSuggestQuest")
    public List<QuestDTO> suggestQuests(String prompt) {
        try {
            String systemText = """
                        Role: Gamified Tutor.
                        Task: Create 3-5 progressive quests (Easy -> Hard) teaching the user's topic.
                        Logic for 'actionType':
                           - quiz: Multiple choice questions.
                           - input_text: Translation/Summary/Writing.
                           - voice_record: Pronunciation/Oral explanation.
                           - file_submission: Coding/Diagrams/Complex creation.
                        Rules:
                           - Title/Desc: RPG-themed but educational.
                           - Rewards: Scale XP/Gold with difficulty.
                    """;
            var request = chatClient.prompt()
                    .user(u -> u.text("Topic: {topic}").param("topic", prompt))
                    .system(systemText); // Short system prompt

            return callAiService(request, new ParameterizedTypeReference<List<QuestDTO>>() {
            });
        } catch (Exception e) {
            log.error("callAiService failed {}", e.getMessage());
            throw e;
        }
    }



    @CircuitBreaker(name = "geminiResilience", fallbackMethod = "fallbackCalAiService")
    private <T> T callAiService(ChatClient.ChatClientRequestSpec request, ParameterizedTypeReference<T> type) {
        try {
            return request.call().entity(type);
        } catch (Exception e) {
            log.error("callAiService failed {}", e.getMessage());
            throw e;
        }

    }

    private <T> T fallbackCalAiService(ChatClient.ChatClientRequestSpec request, ParameterizedTypeReference<T> type, Throwable t) {
        try {
            log.warn("Circuit Breaker triggered or Exception caught: {}. Switching to FALLBACK model: {}", t.getMessage(), fallbackModel);
            GoogleGenAiChatOptions fallbackOptions = GoogleGenAiChatOptions.builder()
                    .model(fallbackModel)
                    .build();
            request.options(fallbackOptions);
            return request.call().entity(type);
        } catch (Exception e) {
            log.error("fallbackCalAiService failed {}", e.getMessage());
            throw e;
        }
    }



}