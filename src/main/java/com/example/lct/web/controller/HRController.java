package com.example.lct.web.controller;

import com.example.lct.model.Employee;
import com.example.lct.model.TaskStage;
import com.example.lct.model.Task;
import com.example.lct.service.HRService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.request.hr.StageDTO;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;
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

        List<Task> tasks = hrService.createTasksPlanForCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), tasksDTO);

        return ResponseEntity.ok().body(tasks);
    }

    @Operation(summary = "update task to Company")
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<Task> updateTaskInfoForCompany(@PathVariable(value = "taskId") Long taskId, @RequestBody TaskDTO taskDTO) {

        Task task = hrService.updateTaskInfoForCompany(taskId, taskDTO);

        return ResponseEntity.ok().body(task);
    }


    @Operation(summary = "create intern to Company")
    @PostMapping("/intern")
    public ResponseEntity<Employee> createInternForCompany(Principal principal, @RequestBody EmployeeForCreateDTO employeeForCreateDTO) {

        Employee employee = hrService.createInternForCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), employeeForCreateDTO);

        return ResponseEntity.ok().body(employee);
    }


    @Operation(summary = "get all task for hr checking")
    @GetMapping("/interns/tasks")
    public ResponseEntity<List<TaskStage>> getAllTaskForChecking(Principal principal) {

        List<TaskStage> tasks = hrService.getAllTaskForCuratorChecking(
                userPrincipalUtils.getEmployeeIdByUserPrincipal(principal)
        );

        return ResponseEntity.ok().body(tasks);
    }

    @Operation(summary = "create stage to intern")
    @PostMapping("/intern/stage")
    public ResponseEntity<Employee> createStageToIntern(Principal principal,
                                                        @RequestBody StageDTO stageDTO) {

        Employee employee = hrService.createStageToIntern(stageDTO);

        return ResponseEntity.ok().body(employee);
    }

    /* TODO
     * + добавление задач по департаментам на каждый уровень (6) (Можно только создать план и сразу приписать задачи)
     * + редактирование задач (нельзя изменить количество задач)
     * + добавление стажёров с привязкой к HR
     * вывод всех задач для проверки
     * проверка задач привязанных к нему стажеров (смена статуса)
     * */
}
