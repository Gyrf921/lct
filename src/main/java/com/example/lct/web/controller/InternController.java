package com.example.lct.web.controller;

import com.example.lct.service.InternService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.intern.TasksToCheckDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/intern")
public class InternController {

    private final InternService internService;
    private final UserPrincipalUtils userPrincipalUtils;

    @Operation(summary = "set Answer To Task")
    @PostMapping("/tasks/{taskStageId}/answer")
    public ResponseEntity<Boolean> setAnswerToTask(@PathVariable(value = "taskStageId") Long taskStageId,
                                                   @RequestBody TasksToCheckDTO answer,
                                                   Principal principal) {

        return ResponseEntity.ok(internService.setAnswerToTask(taskStageId, answer));

    }



    /*
    вывод всех stage текущих задач пользователя из stage в название, статус, дата создания
    вывод задачи по ID


*/

}
