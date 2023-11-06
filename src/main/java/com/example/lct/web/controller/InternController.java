package com.example.lct.web.controller;

import com.example.lct.service.InternService;
import com.example.lct.service.impl.TaskServiceImpl;
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

    @Operation(summary = "sendTasksToChecking")
    @PostMapping("/tasks")
    public ResponseEntity<Boolean> sendTasksToChecking(@RequestBody TasksToCheckDTO tasks){
        return ResponseEntity.ok(internService.sendTasksToCheck(tasks));
    }

    /*
    TODO
     * + прохождение задач
     * покупка мерча (уведомлять HR о том, что он что-то купил)
     * */

}
