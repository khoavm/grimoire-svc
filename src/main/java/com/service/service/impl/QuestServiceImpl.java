package com.service.service.impl;

import com.service.configuration.response.handler.err.exception.RestException;
import com.service.dto.GradingResult;
import com.service.repository.QuestRepository;
import com.service.service.AiService;
import com.service.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestServiceImpl implements QuestService {

    private final AiService aiService;
    private final QuestRepository questRepository;


    @Override
    public GradingResult answerQuest(UUID questId, String userAnswer) {
        try {
            var currentQuest = questRepository.findById(questId).orElseThrow(() -> RestException.NotFound("Not found quest " + questId));
            return aiService.evaluateAnswer(currentQuest.getTitle(), currentQuest.getDescription(), userAnswer);
        } catch (Exception e) {
            log.error("Error in answerQuest {}", e.getMessage());
            throw e;
        }

    }
}
