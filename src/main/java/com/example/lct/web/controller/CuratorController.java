package com.example.lct.web.controller;

import com.example.lct.model.*;
import com.example.lct.service.CuratorService;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.InternService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.hr.StageDTO;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.TestDTO;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;
import com.example.lct.web.dto.response.EmployeeTeamResponseDTO;
import com.example.lct.web.dto.response.TaskForCheckDTO;
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
@RequestMapping("/curator")
public class CuratorController {

    private final CuratorService curatorService;
    private final EmployeeService employeeService;
    private final InternService internService;
    private final UserPrincipalUtils userPrincipalUtils;
/*
--Вывод задания по Id (дубль)

Создание списка задач(общие)
Создание одной задачи(общие)
Редактирование задач(общие)
Добавление базовых заданий(общие) (none Post)

*/

    //region Intern
    @Operation(summary = "get curator interns")
    @GetMapping("/interns")
    public ResponseEntity<List<EmployeeTeamResponseDTO>> getCuratorInterns(Principal principal) {
        log.info("[CuratorController|getCuratorInterns] >> user principal: {}", principal.getName());

        List<EmployeeTeamResponseDTO> employees =
                curatorService.getCuratorInterns(userPrincipalUtils.getEmployeeByUserPrincipal(principal).getEmployeeId());

        log.info("[CuratorController|getCuratorInterns] << result employees.size: {}", employees.size());

        return ResponseEntity.ok().body(employees);
    }

    @Operation(summary = "get all tasks in stages for Intern")
    @GetMapping("/interns/{internId}/tasks")
    public ResponseEntity<List<TaskStage>> getTasksInStagesIntern(@PathVariable(value = "internId") Long internId) {
        log.info("[CuratorController|getStagesForIntern] >> internId: {}", internId);

        List<TaskStage> taskStages = internService.getTasksStageForEmployee(employeeService.getEmployeeById(internId));

        log.info("[CuratorController|getStagesForIntern] << result taskStages.size: {}", taskStages.size());

        return ResponseEntity.ok().body(taskStages);
    }
    @Operation(summary = "get all tasks in stages for Intern")
    @GetMapping("/interns/tasks")
    public ResponseEntity<List<TaskForCheckDTO>> getTasksForChecking(Principal principal) {
        log.info("[CuratorController|getTasksForChecking] >> principal: {}", principal.getName());

        List<TaskForCheckDTO> taskStages = curatorService.getTaskStagesForCuratorChecking(
                userPrincipalUtils.getEmployeeByUserPrincipal(principal).getEmployeeId());

        log.info("[CuratorController|getTasksForChecking] << result taskStages.size: {}", taskStages.size());

        return ResponseEntity.ok().body(taskStages);
    }

    @Operation(summary = "get all tasks in stages for Intern")
    @PatchMapping("/interns/tasks/{taskId}")
    public ResponseEntity<List<TaskForCheckDTO>> evaluateInternAnswer(@PathVariable(value = "taskId") Long taskId,
                                                                @RequestParam(name = "isAccepted") Boolean isAccepted,
                                                                Principal principal) {
        log.info("[CuratorController|evaluateInternAnswer] >> principal: {}", principal.getName());

        curatorService.evaluateInternAnswer(taskId, isAccepted);

        List<TaskForCheckDTO> taskStages =curatorService.getTaskStagesForCuratorChecking(
                userPrincipalUtils.getEmployeeByUserPrincipal(principal).getEmployeeId());

        log.info("[CuratorController|evaluateInternAnswer] << result taskStages.size: {}", taskStages.size());

        return ResponseEntity.ok().body(taskStages);
    }

    @Operation(summary = "create stage to intern")
    @PostMapping("/interns/{internId}/stage")
    public ResponseEntity<List<Stage>> createStageToIntern(@PathVariable(value = "internId") Long internId,
                                                           @RequestBody StageDTO stageDTO,
                                                           Principal principal) {
        log.info("[CuratorController|evaluateInternAnswer] >> principal: {}", principal.getName());

        List<Stage> stages = curatorService.createStageToIntern(internId, stageDTO);

        log.info("[CuratorController|evaluateInternAnswer] << result taskStages.size: {}", stages);

        return ResponseEntity.ok().body(stages);
    }
    @Operation(summary = "set test to intern's stage")
    @PatchMapping("/stage/{stageId}/test")
    public ResponseEntity<Stage> setTestToInternStage(@PathVariable(value = "stageId") Long stageId,
                                                      @RequestBody TestDTO testDTO) {
        log.info("[CuratorController|setTestToInternStage] >> stageId: {}, testDTO: {}", stageId, testDTO);

        Stage stage = curatorService.setTestToStage(stageId, testDTO);

        log.info("[CuratorController|setTestToInternStage] << result stage: {}", stage);

        return ResponseEntity.ok().body(stage);
    }
    //endregion


    //region tasks
    @Operation(summary = "get all company tasks")
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks(Principal principal) {
        log.info("[CuratorController|getTasks] >> user principal: {}", principal.getName());

        Company company = userPrincipalUtils.getCompanyByUserPrincipal(principal);

        log.info("[CuratorController|getTasks] << result: {}", company.getTasks());

        return ResponseEntity.ok().body(company.getTasks());
    }

    @Operation(summary = "create one task")
    @PostMapping("/tasks")
    public ResponseEntity<List<Task>> createTasks(Principal principal, @RequestBody TasksDTO tasksDTO) {

        List<Task> tasks = curatorService.createTasksForCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), tasksDTO);

        return ResponseEntity.ok().body(tasks);
    }
  /*
    @Operation(summary = "create tasks")
    @PostMapping("/tasks")
    public ResponseEntity<List<Task>> createTasks(Principal principal, @RequestBody TaskDTO tasksDTO) {

        List<Task> tasks = hrService.createTasksForCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), tasksDTO);

        return ResponseEntity.ok().body(tasks);
    }



    //endregion



    @Operation(summary = "add list of task to Company")
    @PostMapping("/tasks/base")
    public ResponseEntity<List<Task>> createBaseTasksForCompany(Principal principal, @RequestBody TasksDTO tasksDTO) {

        List<Task> tasks = hrService.createBaseTasksForCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), tasksDTO);

        return ResponseEntity.ok().body(tasks);
    }


    @Operation(summary = "get all tasks by company")
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasksForCompany(Principal principal) {
        return ResponseEntity.ok().body(hrService.getAllTasksForCompany(userPrincipalUtils.getCompanyByUserPrincipal(principal)));
    }

     */

}
