package com.service.dto.entity;

import com.service.constant.QuestActionType;
import com.service.constant.QuestType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class QuestDTO implements Serializable {
    @Id
    private UUID id;
    private String title;
    private String description;
    private String type;
    private QuestActionType actionType;
    private String answerHint;
    private RewardDto reward;
}
