package com.example.lct.web.controller.general;

import com.example.lct.service.EmployeeService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.admin.FilterTeamDTO;
import com.example.lct.web.dto.response.EmployeeTeamResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final EmployeeService employeeService;


    private final UserPrincipalUtils userPrincipalUtils;

    @Operation(summary = "get employees with filter")
    @GetMapping
    public ResponseEntity<List<EmployeeTeamResponseDTO>> getAudios(@RequestParam(required = false, name = "departmentName") String departmentName,
                                                                   @RequestParam(required = false, name = "postName") String postName,
                                                                   @RequestParam(required = false, name = "cityName") String cityName,
                                                                   @RequestParam(required = false, name = "employeeName") String employeeName,
                                                                   Principal principal) {

        FilterTeamDTO filterTeamDTO = new FilterTeamDTO(departmentName, postName, cityName, employeeName);

        //TODO не должен быть пост, но передача параметров не через тело тогда
        List<EmployeeTeamResponseDTO> teamDTOS = employeeService.getTeam(userPrincipalUtils.getCompanyByUserPrincipal(principal), filterTeamDTO);

        return ResponseEntity.ok().body(teamDTOS);
    }

}
