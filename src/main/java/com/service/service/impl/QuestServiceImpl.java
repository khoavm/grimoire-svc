package com.service.service.impl;

import com.service.configuration.response.handler.err.exception.RestException;
import com.service.dto.GradingResult;
import com.service.dto.common.DataList;
import com.service.dto.entity.QuestDTO;
import com.service.dto.request.CreateQuestRequest;
import com.service.dto.request.GetQuestListRequest;
import com.service.dto.request.SuggestQuestRequest;
import com.service.model.Quest;
import com.service.repository.QuestRepository;
import com.service.repository.specification.QuestSpecification;
import com.service.service.AiService;
import com.service.service.QuestService;
import com.service.util.MappingUtil;
import com.service.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestServiceImpl implements QuestService {

    private final AiService aiService;
    private final QuestRepository questRepository;
    private final MappingUtil m;


    @Override
    public GradingResult answerQuest(UUID questId, String userAnswer) {
        try {
            var currentQuest = questRepository.findById(questId).orElseThrow(() -> RestException.NotFound("Not found quest " + questId));
            return aiService.evaluateAnswer(currentQuest.getTitle(), currentQuest.getDescription(), userAnswer);
        } catch (Exception e) {
            log.error("Error in answerQuest {}", e.getMessage());
            throw e;
        }

    }

    @Override
    public DataList<QuestDTO> getQuestList(GetQuestListRequest getQuestListRequest) {
        try {
            DataList<QuestDTO> rs = new DataList<>();
            Pageable pageable = PageRequest.of(getQuestListRequest.getPage(), getQuestListRequest.getSize());
            
            Specification<Quest> spec = QuestSpecification.conjunction();

            if (StringUtils.hasText(getQuestListRequest.getQuery())) {
                spec = spec.and(QuestSpecification.titleOrDescriptionContains(getQuestListRequest.getQuery()));
            }

            if (StringUtils.hasText(getQuestListRequest.getType())) {
                spec = spec.and(QuestSpecification.hasType(getQuestListRequest.getType()));
            }

            var questPageList = questRepository.findAll(spec, pageable);
            rs.setData(questPageList.getContent().stream().map(m::mapQuestToDTO).toList());
            rs.setSize(questPageList.getSize());
            rs.setPage(getQuestListRequest.getPage());
            rs.setTotal(questPageList.getTotalElements());
            return rs;
        } catch (Exception e) {
            log.error("Error in getQuestList {}", e.getMessage());
            throw e;

        }
    }

    @Override
    public QuestDTO createQuest(CreateQuestRequest createQuestRequest) {
        try {
            var quest = m.mapCreateQuestDTOToQuest(createQuestRequest);
            quest.setId(UUIDUtil.generateV7());
            var savedQuest = questRepository.save(quest);
            return m.mapQuestToDTO(savedQuest);
        } catch (Exception e) {
            log.error("Error in createQuest {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<QuestDTO> suggestQuest(SuggestQuestRequest request) {
        return List.of();
    }

    @Override
    public void deleteQuest(UUID questId) {
        try {
            if (!questRepository.existsById(questId)) {
                throw RestException.NotFound("Not found quest " + questId);
            }
            questRepository.deleteById(questId);
        } catch (Exception e) {
            log.error("Error in deleteQuest {}", e.getMessage());
            throw e;
        }
    }
}
