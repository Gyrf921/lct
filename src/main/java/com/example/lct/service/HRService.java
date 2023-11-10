package com.example.lct.service;

import com.example.lct.model.*;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.request.hr.StageDTO;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.TestDTO;
import com.example.lct.web.dto.response.EmployeeTeamResponseDTO;

import java.util.List;

public interface HRService {

    List<EmployeeTeamResponseDTO> createIntern(Company companyByUserPrincipal, EmployeeForCreateDTO employeeForCreateDTO);

    List<EmployeeTeamResponseDTO> createEmployee(Company company, EmployeeForCreateDTO employeeForCreateDTO);

    List<EmployeeTeamResponseDTO> deleteEmployee(Company companyByUserPrincipal, Long employeeId);
}
