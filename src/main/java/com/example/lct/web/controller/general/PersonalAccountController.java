package com.example.lct.web.controller.general;

import com.example.lct.model.History;
import com.example.lct.service.AnalyticalService;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.HistoryService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.EmployeePersonalityDTO;
import com.example.lct.web.dto.response.AnalyticDTO;
import com.example.lct.web.dto.response.EmployeePersonalityResponseDTO;
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
@RequestMapping("/personal-account")
public class PersonalAccountController {

    private final EmployeeService employeeService;
    private final UserPrincipalUtils userPrincipalUtils;
    private final HistoryService historyService;
    private final AnalyticalService analyticalService;

    @Operation(summary = "get employee information")
    @GetMapping
    public ResponseEntity<EmployeePersonalityResponseDTO> getEmployeeInformation(Principal principal) {
        return ResponseEntity.ok().body(
                employeeService.getEmployeeInformation(userPrincipalUtils.getEmployeeByUserPrincipal(principal)));
    }

    @Operation(summary = "set Employee Information")
    @PutMapping()
    public ResponseEntity<?> setEmployeeInformation(Principal principal, @RequestBody EmployeePersonalityDTO employeePersonalityDTO) {
        return ResponseEntity.ok().body(
                employeeService.setEmployeeInformation(userPrincipalUtils.getEmployeeByUserPrincipal(principal), employeePersonalityDTO));
    }

    @Operation(summary = "get history for employee")
    @GetMapping("/analytic")
    public ResponseEntity<List<AnalyticDTO>> getHistoryForEmployee(Principal principal) {
        return ResponseEntity.ok().body(analyticalService
                .getAnalyticByEmployee(userPrincipalUtils.getEmployeeByUserPrincipal(principal)));
    }
}
