package com.service.util;

import com.service.constant.QuestActionType;
import com.service.dto.entity.QuestDTO;
import com.service.dto.entity.RewardDto;
import com.service.dto.request.CreateQuestRequest;
import com.service.model.Quest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MappingUtilTest {

    private MappingUtil mappingUtil;

    @BeforeEach
    void setUp() {
        mappingUtil = new MappingUtil();
    }

    @Test
    void mapQuestToDTO_shouldMapAllFields_whenRewardExists() {
        // given
        Quest quest = new Quest();
        quest.setId(UUIDUtil.generateV7());
        quest.setTitle("Quest title");
        quest.setDescription("Quest description");
        quest.setType("DAILY");
        quest.setActionType(null);
        quest.setAnswerHint("hint");

        Quest.Reward reward = new Quest.Reward();
        reward.setExp(100);
        reward.setGold(50);
        quest.setReward(reward);

        // when
        QuestDTO dto = mappingUtil.mapQuestToDTO(quest);

        // then
        assertNotNull(dto);
        assertEquals("Quest title", dto.getTitle());
        assertEquals("Quest description", dto.getDescription());
        assertEquals("DAILY", dto.getType());
        assertEquals(QuestActionType.unknown, dto.getActionType());
        assertEquals("hint", dto.getAnswerHint());

        assertNotNull(dto.getReward());
        assertEquals(100, dto.getReward().getExp());
        assertEquals(50, dto.getReward().getGold());
    }

    @Test
    void mapQuestToDTO_shouldHandleNullReward() {
        // given
        Quest quest = new Quest();
        quest.setId(UUIDUtil.generateV7());
        quest.setActionType("input_text");
        // when
        QuestDTO dto = mappingUtil.mapQuestToDTO(quest);

        // then
        assertNotNull(dto);
        assertNull(dto.getReward());
    }

    @Test
    void mapCreateQuestDTOToQuest_shouldMapAllFields_whenRewardExists() {
        // given
        CreateQuestRequest request = new CreateQuestRequest();
        request.setTitle("Create title");
        request.setDescription("Create description");
        request.setType("KNOWLEDGE");
        request.setActionType(QuestActionType.input_text);
        request.setAnswerHint("answer hint");

        RewardDto rewardDto = new RewardDto();
        rewardDto.setExp(200);
        rewardDto.setGold(100);
        request.setReward(rewardDto);

        // when
        Quest quest = mappingUtil.mapCreateQuestDTOToQuest(request);

        // then
        assertNotNull(quest);
        assertEquals(request.getTitle(), quest.getTitle());
        assertEquals(request.getDescription(), quest.getDescription());
        assertEquals(request.getType(), quest.getType());
        assertEquals(request.getActionType().toString(), quest.getActionType());
        assertEquals(request.getAnswerHint(), quest.getAnswerHint());

        assertNotNull(quest.getReward());
        assertEquals(rewardDto.getExp(), quest.getReward().getExp());
        assertEquals(rewardDto.getGold(), quest.getReward().getGold());
    }

    @Test
    void mapCreateQuestDTOToQuest_shouldHandleNullActionTypeAndReward() {
        // given
        CreateQuestRequest request = new CreateQuestRequest();
        request.setTitle("Title only");

        // when
        Quest quest = mappingUtil.mapCreateQuestDTOToQuest(request);

        // then
        assertNotNull(quest);
        assertNull(quest.getActionType());
        assertNull(quest.getReward());
    }

    @Test
    void mapList_shouldMapElementsCorrectly() {
        // given
        List<Integer> input = List.of(1, 2, 3);

        // when
        List<String> result = mappingUtil.mapList(input, String::valueOf);

        // then
        assertEquals(3, result.size());
        assertEquals(List.of("1", "2", "3"), result);
    }
}