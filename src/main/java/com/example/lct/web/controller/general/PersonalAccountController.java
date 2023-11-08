package com.example.lct.web.controller.general;

import com.example.lct.service.EmployeeService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.EmployeePersonalityDTO;
import com.example.lct.web.dto.response.EmployeePersonalityResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/personal-account")
public class PersonalAccountController {

    private final EmployeeService employeeService;
    private final UserPrincipalUtils userPrincipalUtils;

    @Operation(summary = "get employee information")
    @GetMapping
    public ResponseEntity<EmployeePersonalityResponseDTO> getEmployeeInformation(Principal principal) {
        return ResponseEntity.ok().body(
                employeeService.getEmployeeInformation(userPrincipalUtils.getEmployeeByUserPrincipal(principal)));
    }

    @Operation(summary = "add article to favourites")
    @PutMapping()
    public ResponseEntity<?> addArticleByIdToFavorite(Principal principal, @RequestBody EmployeePersonalityDTO employeePersonalityDTO) {
        return ResponseEntity.ok().body(
                employeeService.setEmployeeInformation(userPrincipalUtils.getEmployeeByUserPrincipal(principal), employeePersonalityDTO));
    }


    /*
TODO Вывод всех отчивок c пометкой выполненых
* */
}
