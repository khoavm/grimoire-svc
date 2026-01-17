package com.service.dto;

import java.io.Serializable;

public record GradingResult (
        boolean isCorrect,      // Đúng hay sai
        int score,              // Chấm điểm (0-100)
        String feedback,        // Lời nhận xét
        String improvementHint  // Gợi ý cải thiện (nếu sai)
) implements Serializable{ }
