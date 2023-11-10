package com.example.lct.web.controller;

import com.example.lct.model.History;
import com.example.lct.model.TaskStage;
import com.example.lct.model.enumformodel.HistoryType;
import com.example.lct.service.HistoryService;
import com.example.lct.service.InternService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.intern.TasksToCheckDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/intern")
public class InternController {

    private final InternService internService;
    private final UserPrincipalUtils userPrincipalUtils;
    private final HistoryService historyService;

    @Operation(summary = "set Answer To Task")
    @PostMapping("/tasks/{taskStageId}/answer")
    public ResponseEntity<TaskStage> setAnswerAndMarkTaskLikeCompleted(@PathVariable(value = "taskStageId") Long taskStageId,
                                                   @RequestBody TasksToCheckDTO answer,
                                                   Principal principal) {
        //TODO CHECK ATCHIVMENTS
        TaskStage taskStage = internService.setAnswerToTask(taskStageId, answer);
        historyService.createHistoryActionRead(userPrincipalUtils.getEmployeeByUserPrincipal(principal), HistoryType.ARTICLE, "Сдал задачу" + taskStage.getTask().getName());
        return ResponseEntity.ok().body(taskStage);
    }

    @Operation(summary = "get history for employee")
    @GetMapping("/history")
    public ResponseEntity<List<History>> getHistoryForEmployee(Principal principal) {

        return ResponseEntity.ok().body(historyService
                .getAllHistoryForEmployee(userPrincipalUtils.getEmployeeByUserPrincipal(principal)));
    }


    /*@Operation(summary = "mark stage like completed")
    @PostMapping("/stage/{stageId}")
    public ResponseEntity<TaskStage> setTestAnswerAndMarkStageLikeCompleted(@PathVariable(value = "stageId") Long stageId,
                                                                             @RequestBody TasksToCheckDTO answer,
                                                                             Principal principal) {

        TaskStage taskStage = internService.setAnswerToTask(taskStageId, answer);
        historyService.createHistoryActionRead(userPrincipalUtils.getEmployeeByUserPrincipal(principal), HistoryType.ARTICLE, "Сдал задачу" + taskStage.getTask().getName());
        return ResponseEntity.ok().body(taskStage);
    }*/

    /*
    вывод всех stage текущих задач пользователя из stage в название, статус, дата создания
    вывод задачи по ID
*/

}
