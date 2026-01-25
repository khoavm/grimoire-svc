package com.service.service;

import com.service.dto.GradingResult;
import com.service.dto.common.DataList;
import com.service.dto.entity.QuestDTO;
import com.service.dto.request.CreateQuestRequest;
import com.service.dto.request.GetQuestListRequest;
import com.service.dto.request.SuggestQuestRequest;

import java.util.List;
import java.util.UUID;

public interface QuestService {
    GradingResult answerQuest(UUID questId, String userAnswer);
    DataList<QuestDTO> getQuestList(GetQuestListRequest getQuestListRequest);
    QuestDTO createQuest(CreateQuestRequest createQuestRequest);
    List<QuestDTO> suggestQuest(SuggestQuestRequest request);
    void deleteQuest(UUID questId);
}
