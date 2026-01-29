package com.service.service;

import com.service.dto.GradingResult;
import com.service.dto.entity.QuestDTO;

import java.util.List;

public interface AiService {
    GradingResult evaluateAnswer(String questionTitle, String questDescription, String userAnswer);
    List<QuestDTO> suggestQuests(String prompt);
}
