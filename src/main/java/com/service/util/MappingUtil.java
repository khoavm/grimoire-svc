package com.service.util;


import com.service.constant.QuestActionType;
import com.service.dto.entity.QuestDTO;
import com.service.dto.entity.RewardDto;
import com.service.dto.request.CreateQuestRequest;
import com.service.model.Quest;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Component
public class MappingUtil {
    
    public QuestDTO mapQuestToDTO(Quest src) {
        QuestDTO dst = new QuestDTO();
        dst.setId(src.getId());
        dst.setType(src.getType());
        dst.setDescription(src.getDescription());
        dst.setTitle(src.getTitle());
        dst.setActionType(QuestActionType.getVal(src.getActionType()));
        dst.setAnswerHint(src.getAnswerHint());
        if (src.getReward() != null) {
            RewardDto rewardDto = new RewardDto();
            rewardDto.setExp(src.getReward().getExp());
            rewardDto.setGold(src.getReward().getGold());
            dst.setReward(rewardDto);
        }
        return dst;
    }
    
    public Quest mapCreateQuestDTOToQuest(CreateQuestRequest src) {
        var dst = new Quest();
        dst.setTitle(src.getTitle());
        dst.setDescription(src.getDescription());
        dst.setType(src.getType());
        if (src.getActionType() != null) {
            dst.setActionType(src.getActionType().name());
        }
        dst.setAnswerHint(src.getAnswerHint());
        if (src.getReward() != null) {
            Quest.Reward reward = new Quest.Reward();
            reward.setExp(src.getReward().getExp());
            reward.setGold(src.getReward().getGold());
            dst.setReward(reward);
        }
        return dst;
    }

    public <T, R> List<R> mapList(
            List<T> list,
            Function<T, R> mapper
    ) {
        List<R> result = new LinkedList<>();
        for (T item : list) {
            result.add(mapper.apply(item));
        }
        return result;
    }

}
