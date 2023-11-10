package com.example.lct.web.controller.general;

import com.example.lct.model.*;
import com.example.lct.service.TaskService;
import com.example.lct.util.UserPrincipalUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final TaskService taskService;
    private final UserPrincipalUtils userPrincipalUtils;

    @Operation(summary = "get departments from company")
    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getDepartments(Principal principal) {
        log.info("[CompanyController|getDepartments] >> user principal: {}", principal.getName());

        Company company = userPrincipalUtils.getCompanyByUserPrincipal(principal);

        log.info("[CompanyController|getDepartments] << result has companyId: {}", company.getCompanyId());

        return ResponseEntity.ok().body(company.getDepartments());
    }

    @Operation(summary = "get posts from company")
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts(Principal principal) {
        log.info("[CompanyController|getPosts] >> user principal: {}", principal.getName());

        Company company = userPrincipalUtils.getCompanyByUserPrincipal(principal);

        log.info("[CompanyController|getPosts] << result has companyId: {}", company.getCompanyId());

        return ResponseEntity.ok().body(company.getPosts());
    }

    @Operation(summary = "get roles from company")
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles(Principal principal) {
        log.info("[CompanyController|getRoles] >> user principal: {}", principal.getName());

        Company company = userPrincipalUtils.getCompanyByUserPrincipal(principal);

        log.info("[CompanyController|getRoles] << result has companyId: {}", company.getCompanyId());

        return ResponseEntity.ok().body(company.getRoles());
    }
    @Operation(summary = "get tasks from company")
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks(Principal principal) {
        log.info("[CompanyController|getTasks] >> user principal: {}", principal.getName());

        Company company = userPrincipalUtils.getCompanyByUserPrincipal(principal);

        log.info("[CompanyController|getTasks] << result has companyId: {}", company.getCompanyId());

        return ResponseEntity.ok().body(company.getTasks());
    }
    @Operation(summary = "get tasks by id")
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable(name = "taskId") Long taskId,
                                                  Principal principal) {
        log.info("[CompanyController|getTaskById] >> taskId: {}, user principal: {}", taskId, principal.getName());

        Task task = taskService.getTaskById(taskId);

        log.info("[CompanyController|getTaskById] << result is task: {}", task);

        return ResponseEntity.ok().body(task);
    }
}
