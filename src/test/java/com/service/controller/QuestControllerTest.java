package com.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.constant.Constant;
import com.service.dto.request.AnswerQuestRequest;
import com.service.dto.GradingResult;
import com.service.service.QuestService;
import com.service.util.ResponseUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = QuestController.class,
        // Loại bỏ các Filter ra khỏi context của bài test này
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        // Điền tên class Filter gây lỗi vào đây
                        com.service.configuration.filter.TracerFilter.class
                }
        )
)
@Import({ResponseUtil.class})
class QuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuestService questService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void answerQuest_Success() throws Exception {
        // Arrange
        UUID questId = UUID.randomUUID();
        String userAnswer = "My Answer";

        AnswerQuestRequest request = new AnswerQuestRequest();
        request.setAnswer(userAnswer);
        request.setQuestionDescription("Some description");

        GradingResult result = new GradingResult(true, 100, "Excellent", "None");

        when(questService.answerQuest(questId, userAnswer)).thenReturn(result);

        // Act & Assert
        mockMvc.perform(post(Constant.API.QUEST + "/{questId}/answer", questId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.isCorrect").value(true))
                .andExpect(jsonPath("$.data.score").value(100))
                .andExpect(jsonPath("$.data.feedback").value("Excellent"));
    }
}
