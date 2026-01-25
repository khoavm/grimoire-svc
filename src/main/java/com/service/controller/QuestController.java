package com.service.controller;

import com.service.configuration.response.body.ApiResponse;
import com.service.constant.Constant;
import com.service.dto.common.DataList;
import com.service.dto.entity.QuestDTO;
import com.service.dto.request.AnswerQuestRequest;
import com.service.dto.request.CreateQuestRequest;
import com.service.dto.request.GetQuestListRequest;
import com.service.dto.GradingResult;
import com.service.dto.request.SuggestQuestRequest;
import com.service.service.QuestService;
import com.service.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@Tag(name = "Quest Controller")
@RequestMapping(Constant.API.QUEST)
public class QuestController {

    private final QuestService questService;
    private final ResponseUtil r;

    @Operation(summary = "Answer Quest")
    @PostMapping("/{questId}/answer")
    public ApiResponse<GradingResult> answerQuest(@PathVariable UUID questId, @RequestBody AnswerQuestRequest request) {
        try{
            return r.ok(questService.answerQuest(questId, request.getAnswer()));
        }catch (Exception e){
            log.error("Error in answerQuest {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Delete Quest")
    @DeleteMapping("/{questId}")
    public ApiResponse<GradingResult> deleteQuest(@PathVariable UUID questId) {
        try{
            questService.deleteQuest(questId);
            return r.ok();
        }catch (Exception e){
            log.error("Error in deleteQuest {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Get Quest List")
    @PostMapping("/list")
    public ApiResponse<DataList<QuestDTO>> getQuestList(@RequestBody GetQuestListRequest request) {
        try{
            return r.ok(questService.getQuestList(request));
        }catch (Exception e){
            log.error("Error in getQuestList {}", e.getMessage());
            throw e;
        }
    }


    @Operation(summary = "Create Quest")
    @PostMapping()
    public ApiResponse<QuestDTO> createQuest(@RequestBody CreateQuestRequest request) {
        try{
            return r.ok(questService.createQuest(request));
        }catch (Exception e){
            log.error("Error in createQuest {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Suggest Quests")
    @PostMapping("/suggest")
    public ApiResponse<List<QuestDTO>> suggestQuest(@RequestBody SuggestQuestRequest request) {
        try{
            return r.ok(questService.suggestQuest(request));
        }catch (Exception e){
            log.error("Error in suggestQuest {}", e.getMessage());
            throw e;
        }

    }



}
