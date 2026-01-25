package com.service.service.impl;

import com.service.configuration.response.handler.err.exception.RestException;
import com.service.dto.GradingResult;
import com.service.model.Quest;
import com.service.repository.QuestRepository;
import com.service.service.AiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestServiceImplTest {

    @Mock
    private AiService aiService;

    @Mock
    private QuestRepository questRepository;

    @InjectMocks
    private QuestServiceImpl questService;

    @Test
    void answerQuest_Success() {
        // Arrange
        UUID questId = UUID.randomUUID();
        String userAnswer = "My Answer";
        Quest quest = new Quest(questId, "Title", "Desc", "Type", "Action", "",new Quest.Reward(10, 100));
        GradingResult expectedResult = new GradingResult(true, 100, "Good", "None");

        when(questRepository.findById(questId)).thenReturn(Optional.of(quest));
        when(aiService.evaluateAnswer(quest.getTitle(), quest.getDescription(), userAnswer))
                .thenReturn(expectedResult);

        // Act
        GradingResult result = questService.answerQuest(questId, userAnswer);

        // Assert
        assertThat(result).isEqualTo(expectedResult);
        verify(questRepository).findById(questId);
        verify(aiService).evaluateAnswer(quest.getTitle(), quest.getDescription(), userAnswer);
        verifyNoMoreInteractions(questRepository, aiService);
    }

    @Test
    void answerQuest_NotFound() {
        // Arrange
        UUID questId = UUID.randomUUID();
        String userAnswer = "My Answer";

        when(questRepository.findById(questId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestException.class, () -> questService.answerQuest(questId, userAnswer));
        verify(questRepository).findById(questId);
        verifyNoMoreInteractions(questRepository, aiService);
    }
}
