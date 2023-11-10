package com.example.lct.web.controller;

import com.example.lct.model.Employee;
import com.example.lct.model.Stage;
import com.example.lct.model.Task;
import com.example.lct.model.TaskStage;
import com.example.lct.service.CuratorService;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.HRService;
import com.example.lct.service.InternService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.request.hr.StageDTO;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.TestDTO;
import com.example.lct.web.dto.response.EmployeeTeamResponseDTO;
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
    //region Intern
    @Operation(summary = "get curator interns")
    @GetMapping("/interns")
    public ResponseEntity<List<EmployeeTeamResponseDTO>> getCuratorInterns(Principal principal) {

        List<EmployeeTeamResponseDTO> employees = curatorService.getAllInternForHR(userPrincipalUtils.getEmployeeByUserPrincipal(principal).getEmployeeId());

        return ResponseEntity.ok().body(employees);
    }

    @Operation(summary = "get all tasks for Intern")
    @GetMapping("/interns/{internId}/tasks")
    public ResponseEntity<List<TaskStage>> getStagesForIntern(@PathVariable(value = "internId") Long internId) {
        return ResponseEntity.ok().body(internService.getTasksStageForEmployee(employeeService.getEmployeeById(internId)));
    }


    @Operation(summary = "create stage to intern")
    @PostMapping("/interns/{internId}/stage")
    public ResponseEntity<List<Stage>> createStageToIntern(@PathVariable(value = "internId") Long internId,
                                                           @RequestBody StageDTO stageDTO,
                                                           Principal principal) {
        Employee employee = hrService.createStageToIntern(internId, stageDTO);

        return ResponseEntity.ok().body(employee.getStages());
    }
    @Operation(summary = "set test to intern's stage")
    @PatchMapping("/stage/{stageId}/test")
    public ResponseEntity<List<TaskStage>> setTestToInternStage(@PathVariable(value = "stageId") Long stageId,
                                                                @RequestBody TestDTO testDTO) {

        Stage stage = curatorService.setTestToStage(stageId, testDTO);

        return ResponseEntity.ok().body(stage);
    }

    //endregion




    @Operation(summary = "add list of task to Company")
    @PostMapping("/tasks")
    public ResponseEntity<List<Task>> createTasksForCompany(Principal principal, @RequestBody TasksDTO tasksDTO) {

        List<Task> tasks = hrService.createTasksForCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), tasksDTO);

        return ResponseEntity.ok().body(tasks);
    }

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
