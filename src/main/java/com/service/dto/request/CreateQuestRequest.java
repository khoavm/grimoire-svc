package com.service.dto.request;

import com.service.constant.QuestActionType;
import com.service.dto.entity.RewardDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CreateQuestRequest implements Serializable {
    private String title;
    private String description;
    private String type;
    private QuestActionType actionType;
    private String answerHint;
    private RewardDto reward;
}
