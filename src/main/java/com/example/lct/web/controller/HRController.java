package com.example.lct.web.controller;

import com.example.lct.model.Employee;
import com.example.lct.model.Stage;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.HRService;
import com.example.lct.service.InternService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
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
@RequestMapping("/hr")
public class HRController {

    private final HRService hrService;

    private final UserPrincipalUtils userPrincipalUtils;

    @Operation(summary = "create intern to Company")
    @PostMapping("/employee")
    public ResponseEntity<List<EmployeeTeamResponseDTO>> createEmployee(Principal principal,
                                                                        @RequestBody EmployeeForCreateDTO employeeForCreateDTO) {
        log.info("[HRController|createEmployee] >> user principal: {}, employeeForCreateDTO: {}", principal.getName(), employeeForCreateDTO);

        List<EmployeeTeamResponseDTO> employees = hrService.createEmployee(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), employeeForCreateDTO);

        log.info("[HRController|createEmployee] << result employees.size: {}", employees.size());

        return ResponseEntity.ok().body(employees);
    }

    @Operation(summary = "create intern to Company")
    @PostMapping("/intern")
    public ResponseEntity<List<EmployeeTeamResponseDTO>> createIntern(Principal principal,
                                                                      @RequestBody EmployeeForCreateDTO employeeForCreateDTO) {
        log.info("[HRController|createIntern] >> user principal: {}, employeeForCreateDTO: {}", principal.getName(), employeeForCreateDTO);

        List<EmployeeTeamResponseDTO> employees = hrService.createIntern(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), employeeForCreateDTO);

        log.info("[HRController|createIntern] << result employees.size: {}", employees.size());
        return ResponseEntity.ok().body(employees);
    }

    @Operation(summary = "delete employee by id")
    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<List<EmployeeTeamResponseDTO>> deleteEmployee(@PathVariable(name = "employeeId") Long employeeId,
                                                                      Principal principal) {
        log.info("[HRController|deleteEmployee] >> user principal: {}, employeeId: {}", principal.getName(), employeeId);

        List<EmployeeTeamResponseDTO> employees = hrService.deleteEmployee(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), employeeId);

        log.info("[HRController|deleteEmployee] << result employees.size: {}", employees.size());
        return ResponseEntity.ok().body(employees);
    }
}
