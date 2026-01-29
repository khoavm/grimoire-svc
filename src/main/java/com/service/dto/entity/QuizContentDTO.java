package com.service.dto.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuizContentDTO implements Serializable {
    private List<String> options;
    private String correctAnswer;
    private String explanation;
}
