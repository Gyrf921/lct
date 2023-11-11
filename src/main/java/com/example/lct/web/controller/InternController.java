package com.example.lct.web.controller;

import com.example.lct.model.History;
import com.example.lct.model.Stage;
import com.example.lct.model.TaskStage;
import com.example.lct.model.enumformodel.HistoryType;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.HistoryService;
import com.example.lct.service.InternService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.intern.TasksToCheckDTO;
import com.example.lct.web.dto.response.StageResponseDTO;
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
@RequestMapping("/interns")
public class InternController {

    private final InternService internService;
    private final EmployeeService employeeService;

    private final UserPrincipalUtils userPrincipalUtils;

    private final HistoryService historyService;

    /*
Вывод общего задания по Id (дубль)
Добавление ответов на задания (уведомление куратора)
Добавление ответов на тест (уведомление куратора)
    * */

    @Operation(summary = "get all tasks in stages for Intern")
    @GetMapping("/{internId}/tasks")
    public ResponseEntity<List<StageResponseDTO>> getTasksInStagesIntern(@PathVariable(value = "internId") Long internId) {
        log.info("[CuratorController|getStagesForIntern] >> internId: {}", internId);

        List<StageResponseDTO> stages = internService.getStageForEmployee(employeeService.getEmployeeById(internId));

        log.info("[CuratorController|getStagesForIntern] << result stages.size: {}", stages.size());

        return ResponseEntity.ok().body(stages);
    }

    @Operation(summary = "set Answer To Task")
    @PostMapping("/tasks/{taskStageId}/answer")
    public ResponseEntity<TaskStage> setAnswerAndMarkTaskLikeCompleted(@PathVariable(value = "taskStageId") Long taskStageId,
                                                                       @RequestBody TasksToCheckDTO answer,
                                                                       Principal principal) {
        TaskStage taskStage = internService.setAnswerToTask(userPrincipalUtils.getEmployeeByUserPrincipal(principal), taskStageId, answer);
        historyService.createHistoryActionCompleted(userPrincipalUtils.getEmployeeByUserPrincipal(principal), HistoryType.TASK, "Сдал задачу" + taskStage.getTask().getName());
        return ResponseEntity.ok().body(taskStage);
    }

    @Operation(summary = "mark stage like completed")
    @PostMapping("/stage/{stageId}/test/answer")
    public ResponseEntity<TaskStage> setTestAnswerAndMarkStageLikeCompleted(@PathVariable(value = "stageId") Long stageId,
                                                                             @RequestBody TasksToCheckDTO answer,
                                                                             Principal principal) {
        TaskStage taskStage = internService.setAnswerToTask(userPrincipalUtils.getEmployeeByUserPrincipal(principal), stageId, answer);
        historyService.createHistoryActionCompleted(userPrincipalUtils.getEmployeeByUserPrincipal(principal), HistoryType.TEST, "Сдал тест" + taskStage.getTask().getName());
        return ResponseEntity.ok().body(taskStage);
    }

    /*
    вывод всех stage текущих задач пользователя из stage в название, статус, дата создания
    вывод задачи по ID
*/

}
