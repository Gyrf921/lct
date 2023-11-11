package com.example.lct.web.controller.general;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.model.Employee;
import com.example.lct.service.AdminService;
import com.example.lct.service.EmployeeService;
import com.example.lct.web.dto.request.AuthorizationUserDTO;
import com.example.lct.web.dto.request.RegistrationUserDTO;
import com.example.lct.web.dto.request.admin.RegistrationCompanyDTO;
import com.example.lct.web.dto.response.CompanyAndJwtResponseDTO;
import com.example.lct.web.dto.response.obj.JwtResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login")
public class AuthController {

    private final EmployeeService employeeService;

    private final AuthenticationManager authenticationManager;

    private final AdminService adminService;

    @Operation(summary = "enterForEmployee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/authentication")
    public ResponseEntity<JwtResponseDTO> enterForEmployee(@RequestBody AuthorizationUserDTO authorizationUserDTO) {
        log.info("[enterForEmployee] >> create token for email: {}", authorizationUserDTO.getEmail());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authorizationUserDTO.getEmail(), authorizationUserDTO.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            log.error(badCredentialsException.getMessage());
            throw new ResourceNotFoundException(badCredentialsException.getMessage());
        }

        JwtResponseDTO token = employeeService.createTokenForUser(authorizationUserDTO.getEmail());

        log.info("[createToken] << result is role: {}", token.getRoles());

        return ResponseEntity.ok().body(token);
    }

    @Operation(summary = "Registration and create Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/registration")
    public ResponseEntity<JwtResponseDTO> createUser(@RequestBody RegistrationUserDTO registrationUserDTO) {
        log.info("[createUser] >> create user with name: {}", registrationUserDTO.getName());

        Employee employee = employeeService.registrationEmployee(registrationUserDTO);

        JwtResponseDTO token = employeeService.createTokenForUser(employee.getEmail());
        log.info("[createUser] << result is token");

        return ResponseEntity.ok().body(token);
    }

    @Operation(summary = "registration Company and create admin")
    @PostMapping("/company")
    public ResponseEntity<CompanyAndJwtResponseDTO> registrationCompanyAndCreateAdmin(@RequestBody RegistrationCompanyDTO registrationCompanyDTO) {
        log.info("[registrationCompanyAndCreateAdmin] >> registrationCompanyDTO: {}", registrationCompanyDTO.getCompanyDTO());

        CompanyAndJwtResponseDTO adminInfo = adminService.createCompany(registrationCompanyDTO);

        log.info("[registrationCompanyAndCreateAdmin] << result is token and company info");

        return ResponseEntity.ok().body(adminInfo);
    }
}
