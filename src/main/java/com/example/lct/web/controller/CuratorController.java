package com.example.lct.web.controller;

import com.example.lct.model.*;
import com.example.lct.model.enumformodel.HistoryType;
import com.example.lct.service.AnalyticalService;
import com.example.lct.service.CuratorService;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.InternService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.hr.StageDTO;
import com.example.lct.web.dto.request.hr.StageWithoutTasksDTO;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.TestDTO;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;
import com.example.lct.web.dto.response.AnalyticDTO;
import com.example.lct.web.dto.response.EmployeeTeamResponseDTO;
import com.example.lct.web.dto.response.StageResponseDTO;
import com.example.lct.web.dto.response.TaskForCheckDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/curator")
public class CuratorController {

    private final CuratorService curatorService;
    private final EmployeeService employeeService;
    private final InternService internService;
    private final AnalyticalService analyticalService;
    private final UserPrincipalUtils userPrincipalUtils;

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
    public ResponseEntity<List<StageResponseDTO>> getTasksInStagesIntern(@PathVariable(value = "internId") Long internId) {
        log.info("[CuratorController|getStagesForIntern] >> internId: {}", internId);

        List<StageResponseDTO> stages = internService.getStageForEmployee(employeeService.getEmployeeById(internId));

        log.info("[CuratorController|getStagesForIntern] << result stages.size: {}", stages.size());

        return ResponseEntity.ok().body(stages);
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
    @PatchMapping("/interns/{internId}/tasks/{taskId}")
    public ResponseEntity<List<TaskForCheckDTO>> evaluateInternAnswer(@PathVariable(value = "internId") Long internId,
                                                                      @PathVariable(value = "taskId") Long taskId,
                                                                      @RequestParam(name = "isAccepted") Boolean isAccepted,
                                                                      Principal principal) {
        log.info("[CuratorController|evaluateInternAnswer] >> principal: {}", principal.getName());

        curatorService.evaluateInternAnswer(internId, taskId, isAccepted);

        List<TaskForCheckDTO> taskStages = curatorService.getTaskStagesForCuratorChecking(
                userPrincipalUtils.getEmployeeByUserPrincipal(principal).getEmployeeId());

        log.info("[CuratorController|evaluateInternAnswer] << result taskStages.size: {}", taskStages.size());

        return ResponseEntity.ok().body(taskStages);
    }

    @Operation(summary = "create stage to intern")
    @PostMapping("/interns/{internId}/stage")
    public ResponseEntity<List<StageResponseDTO>> createStageToIntern(@PathVariable(value = "internId") Long internId,
                                                           @RequestBody StageDTO stageDTO,
                                                           Principal principal) {
        log.info("[CuratorController|createStageToIntern] >> principal: {}", principal.getName());
        List<StageResponseDTO> stages;
        if (stageDTO.getTasksId() == null || stageDTO.getTasksId().isEmpty()){
            stages = curatorService.createStageToInternWithoutTask(internId,
                    new StageWithoutTasksDTO(stageDTO.getName(), stageDTO.getLevelDifficulty(), stageDTO.getDeadline()));
        }
        else {
            stages = curatorService.createStageToIntern(internId, stageDTO);
        }

        log.info("[CuratorController|createStageToIntern] << result stages");

        return ResponseEntity.ok().body(stages);
    }

    @Operation(summary = "create stage to intern")
    @PostMapping("/interns/{internId}/stage/non-task")
    public ResponseEntity<List<StageResponseDTO>> createStageToInternWithoutTask(@PathVariable(value = "internId") Long internId,
                                                                      @RequestBody StageWithoutTasksDTO stageWithoutTasksDTO,
                                                                      Principal principal) {
        log.info("[CuratorController|createStageToInternWithoutTask] >> principal: {}", principal.getName());

        List<StageResponseDTO> stages = curatorService.createStageToInternWithoutTask(internId, stageWithoutTasksDTO);

        log.info("[CuratorController|createStageToInternWithoutTask] << result taskStages.size: {}", stages);

        return ResponseEntity.ok().body(stages);
    }
    @Operation(summary = "set task to intern's stage")
    @PostMapping("/stage/{stageId}/tasks/{taskId}")
    public ResponseEntity<List<StageResponseDTO>> setTaskToInternStage(@PathVariable(value = "stageId") Long stageId,
                                                      @PathVariable(value = "taskId") Long taskId) {
        log.info("[CuratorController|setTaskToInternStage] >> stageId: {}, taskId: {}", stageId, taskId);

        List<StageResponseDTO> stage = curatorService.setTaskToStage(stageId, taskId);

        log.info("[CuratorController|setTaskToInternStage] << result stage: {}", stage);

        return ResponseEntity.ok().body(stage);
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

    //region Tasks
    @Operation(summary = "get all company tasks")
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks(Principal principal) {
        log.info("[CuratorController|getTasks] >> user principal: {}", principal.getName());

        Company company = userPrincipalUtils.getCompanyByUserPrincipal(principal);

        log.info("[CuratorController|getTasks] << result: {}", company.getTasks());

        return ResponseEntity.ok().body(company.getTasks());
    }

    @Operation(summary = "create task")
    @PostMapping("/tasks")
    public ResponseEntity<List<Task>> createTasks(Principal principal, @RequestBody TasksDTO tasksDTO) {
        log.info("[CuratorController|createTasks] >> user principal: {}, tasksDTO: {}", principal.getName(), tasksDTO);

        List<Task> tasks = curatorService.createTasks(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), tasksDTO.getTaskDTOList());

        log.info("[CuratorController|createTasks] << result: {}", tasks);
        return ResponseEntity.ok().body(tasks);
    }

    @Operation(summary = "create one tasks")
    @PostMapping("/task")
    public ResponseEntity<List<Task>> createTask(Principal principal, @RequestBody TaskDTO taskDTO) {
        log.info("[CuratorController|createTask] >> user principal: {}, taskDTO: {}", principal.getName(), taskDTO);

        List<Task> tasks = curatorService.createTask(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), taskDTO);

        log.info("[CuratorController|createTask] << result: {}", tasks);
        return ResponseEntity.ok().body(tasks);
    }

    @Operation(summary = "update task")
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<List<Task>> updateTask(@PathVariable(name = "taskId") Long taskId,
                                                  Principal principal,
                                                  @RequestBody TaskDTO taskDTO) {
        log.info("[CuratorController|updateTask] >> taskId: {}, user principal: {}, tasksDTO: {}", taskId, principal.getName(), taskDTO);

        List<Task> tasks = curatorService.updateTask(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), taskId, taskDTO);

        log.info("[CuratorController|updateTask] << result: {}", tasks);

        return ResponseEntity.ok().body(tasks);
    }
    //endregion

    //region Analytic
    @Operation(summary = "get analytic by department")
    @GetMapping("/analytic")
    public ResponseEntity<List<AnalyticDTO>> getAnalyticByDepartment(@RequestParam(name = "departmentName" , required = false) String departmentName, Principal principal) {
        log.info("[CuratorController|getAnalyticByDepartment] >> user principal: {}, departmentName: {}", principal.getName(), departmentName);

        List<AnalyticDTO> analyticByDepartment =analyticalService.getAnalyticByDepartment(userPrincipalUtils.getCompanyByUserPrincipal(principal), departmentName);

        log.info("[CuratorController|getAnalyticByDepartment] << result analyticByDepartment: {}", analyticByDepartment);

        return ResponseEntity.ok().body(analyticByDepartment);
    }
    //endregion
}
