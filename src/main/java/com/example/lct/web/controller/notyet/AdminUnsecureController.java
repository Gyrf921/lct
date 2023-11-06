package com.example.lct.web.controller.notyet;

import com.example.lct.service.AdminService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.admin.*;
import com.example.lct.web.dto.response.CompanyAndJwtResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/unsecure")
public class AdminUnsecureController {

    private final AdminService adminService;
    private final UserPrincipalUtils userPrincipalUtils;

    @Operation(summary = "registration Company and create admin")
    @PostMapping("/company")
    public ResponseEntity<CompanyAndJwtResponseDTO> registrationCompanyAndCreateAdmin(@RequestBody RegistrationCompanyDTO registrationCompanyDTO) {

        CompanyAndJwtResponseDTO adminInfo = adminService.createCompany(registrationCompanyDTO);

        //TODO return JWT token and company info (mb HashMap)
        return ResponseEntity.ok().body(adminInfo);
    }

/* TODO
 * регистрация компании (роли подргужаются сами)
 * добавление департаментов
 * добавление сотрудников
 * добавление вопросов
 * добавление мерча
 * добавление баз знаний
 * */
}
