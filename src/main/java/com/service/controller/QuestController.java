package com.service.controller;

import com.service.configuration.response.body.ApiResponse;
import com.service.constant.Constant;
import com.service.dto.AnswerQuestRequest;
import com.service.dto.GradingResult;
import com.service.service.AiService;
import com.service.service.QuestService;
import com.service.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "answerQuest")
    @PostMapping("/{questId}/answer")
    public ApiResponse<GradingResult> answerQuest(@PathVariable UUID questId, @RequestBody AnswerQuestRequest request) {
        return r.ok(questService.answerQuest(questId, request.getAnswer()));
    }

}
