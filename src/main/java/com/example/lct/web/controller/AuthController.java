package com.example.lct.web.controller;

import com.example.lct.model.Employee;
import com.example.lct.service.EmployeeService;
import com.example.lct.web.dto.request.AuthorizationUserDTO;
import com.example.lct.web.dto.request.RegistrationUserDTO;
import com.example.lct.web.dto.response.JwtResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login")
public class AuthController {

    private final EmployeeService employeeService;

    private final AuthenticationManager authenticationManager;


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
            throw badCredentialsException;
        }

        // TODO rename
        String token = employeeService.createTokenForUser(authorizationUserDTO.getEmail());

        log.info("[createToken] << result is token: {}", token);

        return ResponseEntity.ok().body(new JwtResponseDTO(token));
    }

    @Operation(summary = "Registration and create Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/registration")
    public ResponseEntity<JwtResponseDTO> createUser(@RequestBody RegistrationUserDTO registrationUserDTO) {
        log.info("[createUser] >> create user with name: {}", registrationUserDTO.getName());

        Employee employee = employeeService.registrationEmployee(registrationUserDTO);

        String token = employeeService.createTokenForUser(employee.getEmail());
        log.info("[createUser] << result is token");

        return ResponseEntity.ok().body(new JwtResponseDTO(token));
    }

}
