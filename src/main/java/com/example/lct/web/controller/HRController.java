package com.example.lct.web.controller;

import com.example.lct.model.Employee;
import com.example.lct.model.Stage;
import com.example.lct.model.Task;
import com.example.lct.model.TaskStage;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.HRService;
import com.example.lct.service.InternService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.request.hr.StageDTO;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.TestDTO;
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
@RequestMapping("/hr")
public class HRController {

    private final HRService hrService;
    private final EmployeeService employeeService;
    private final InternService internService;
    private final UserPrincipalUtils userPrincipalUtils;

    @Operation(summary = "get all interns for hr")
    @GetMapping("/interns")
    public ResponseEntity<List<Employee>> getAllHRsIntern(Principal principal) {

        List<Employee> employees = hrService.getAllInternForHR(userPrincipalUtils.getEmployeeIdByUserPrincipal(principal));

        return ResponseEntity.ok().body(employees);
    }

    @Operation(summary = "get all tasks by company")
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasksForCompany(Principal principal) {
        return ResponseEntity.ok().body(hrService.getAllTasksForCompany(userPrincipalUtils.getCompanyByUserPrincipal(principal)));
    }


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

    @Operation(summary = "create intern to Company")
    @PostMapping("/intern")
    public ResponseEntity<List<Stage>> createInternForCompany(Principal principal, @RequestBody EmployeeForCreateDTO employeeForCreateDTO) {

        Employee employee = hrService.createInternForCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), employeeForCreateDTO);

        return ResponseEntity.ok().body(employee.getStages());
    }


    @Operation(summary = "create stage to intern")
    @PostMapping("/interns/{internId}/stage")
    public ResponseEntity<List<Stage>> createStageToIntern(@PathVariable(value = "internId") Long internId,
                                                        @RequestBody StageDTO stageDTO,
                                                        Principal principal) {
        Employee employee = hrService.createStageToIntern(internId, stageDTO);

        return ResponseEntity.ok().body(employee.getStages());
    }

    @Operation(summary = "create stage to intern")
    @PatchMapping("/stage/{stageId}/test")
    public ResponseEntity<Stage> setTestToStage(@PathVariable(value = "stageId") Long stageId,
                                                @RequestBody TestDTO testDTO,
                                                Principal principal) {

        Stage stage = hrService.setTestToStage(stageId, testDTO);

        return ResponseEntity.ok().body(stage);
    }

    @Operation(summary = "get all tasks for Intern")
    @GetMapping("/tasks/{internId}")
    public ResponseEntity<List<TaskStage>> getStagesForIntern(@PathVariable(value = "internId") Long internId,
                                                              Principal principal) {
        return ResponseEntity.ok().body(internService.getTasksStageForEmployee(employeeService.getEmployeeById(internId)));
    }


    /* TODO
     * + добавление задач по департаментам на каждый уровень (6) (Можно только создать план и сразу приписать задачи)
     * + редактирование задач (нельзя изменить количество задач)
     * + добавление стажёров с привязкой к HR
     * вывод всех задач для проверки
     * проверка задач привязанных к нему стажеров (смена статуса)
     * */
}
