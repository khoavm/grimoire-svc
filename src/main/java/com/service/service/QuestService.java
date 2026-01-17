package com.service.service;

import com.service.dto.GradingResult;

import java.util.UUID;

public interface QuestService {
    GradingResult answerQuest(UUID questId, String userAnswer);
}
