package com.service.service;

import com.service.dto.GradingResult;

public interface AiService {
    GradingResult evaluateAnswer(String questionTitle, String questDescription, String userAnswer);
}
