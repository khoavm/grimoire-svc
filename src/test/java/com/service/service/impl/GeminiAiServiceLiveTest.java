package com.service.service.impl;

import com.service.dto.GradingResult;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("live")
class GeminiAiServiceLiveTest {

    @Autowired
    private GeminiAiService geminiAiService;

    @Autowired
    private Environment env;

    @Test
    void debugConfiguration() {

        String key = env.getProperty("spring.ai.google.genai.api-key"); // Hoặc key bạn dùng
        System.out.println("========================================");
        System.out.println("DEBUG API KEY: " + key);
        System.out.println("========================================");
    }

    @Test
    void evaluateAnswer_LiveCall_ShouldReturnResult() {
        // Arrange
        // Dùng một câu hỏi thật để AI dễ trả lời
        String title = "Java Programming Language";
        String description = "What is the keyword to define a constant in Java?";
        String userAnswer = "final"; // Câu trả lời đúng
        // Act
        // Đây là cuộc gọi THẬT lên Google Gemini
        GradingResult result = geminiAiService.evaluateAnswer(title, description, userAnswer);
        // Assert
        // Chúng ta không thể assert chính xác 100% text feedback vì AI trả lời ngẫu nhiên
        // Nhưng logic đúng sai thì phải chuẩn.
        assertThat(result).isNotNull();

        // Kiểm tra cấu trúc trả về có đúng không
        assertThat(result.isCorrect())
                .as("User answer 'final' should be correct")
                .isTrue();

        assertThat(result.score())
                .as("Confidence score should be high for correct answer")
                .isGreaterThan(80); // Thường đúng thì điểm sẽ cao

        assertThat(result.improvementHint()).isNotEmpty();

    }

    @Test
    void evaluateAnswer_LiveCall_WrongAnswer() {
        // Arrange
        String title = "Java Programming Language";
        String description = "What is the keyword to define a constant in Java?";
        String userAnswer = "var"; // Câu trả lời sai

        // Act
        GradingResult result = geminiAiService.evaluateAnswer(title, description, userAnswer);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.isCorrect())
                .as("User answer 'var' should be incorrect")
                .isFalse();

        System.out.println("AI Response (Wrong answer): " + result);
    }
}
