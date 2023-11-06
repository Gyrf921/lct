package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.exception.UserAlreadyExistException;
import com.example.lct.mapper.EmployeeMapper;
import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.model.factory.EmployeeFactory;
import com.example.lct.model.status.Department;
import com.example.lct.model.status.Role;
import com.example.lct.repository.EmployeeRepository;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.RoleService;
import com.example.lct.util.JwtTokenUtils;
import com.example.lct.web.dto.request.RegistrationUserDTO;
import com.example.lct.web.dto.request.admin.obj.EmployeeDTO;
import com.example.lct.web.dto.request.admin.EmployeesDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements UserDetailsService, EmployeeService {
    private final JwtTokenUtils jwtTokenUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    private final RoleService roleService;
    private final DepartmentServiceImpl departmentService;

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;


    @Override
    public Employee getEmployeeByEmail(String email) {
        log.info("[getUserByEmail] >> email: {}", email);

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Employee not found by this email :{} ", email);
                    return new ResourceNotFoundException("Employee not found by this email :: " + email);
                });

        log.info("[getUserByEmail] << result: {}", employee.getName());

        return employee;
    }

    @Override
    public String createTokenForUser(String email) {
        log.info("[createTokenForUser] >> create token for email: {}", email);

        UserDetails userDetails = loadUserByUsername(email);

        log.info("[createTokenForUser] << result is token");

        return jwtTokenUtils.generateToken(userDetails);
    }

    @Override
    public Employee createIntern(Long companyId, Long hrId, EmployeeDTO employeeDTO) {

        Employee intern = employeeRepository.save(
                EmployeeFactory.createIntern(companyId, hrId, employeeDTO));

        log.info("[createIntern] << result : {}", intern);

        return intern;
    }

    @Override
    public List<Employee> getAllInternByCuratorId(Long employeeIdByUserPrincipal) {
        List<Employee> interns = employeeRepository.findAllByCuratorId(employeeIdByUserPrincipal);

        log.info("[getAllInternByCuratorId] << result : {}", interns);

        return interns;
    }

    @Override
    public List<Employee> createEmployeesByAdmin(Long companyId, EmployeesDTO employeesByAdmin) {

        List<Employee> employeeSavedList = new ArrayList<>();

        for (EmployeeDTO employeeDTO: employeesByAdmin.getEmployeeDTOList()) {
            employeeSavedList.add(EmployeeFactory.createEmployee(companyId, employeeDTO));
        }

        List<Employee> savedEmployees = employeeRepository.saveAll(employeeSavedList);

        log.info("[createEmployeesByAdmin] << result : {}", savedEmployees.size());

        return savedEmployees;
    }

    @Override
    public Employee registrationEmployee(RegistrationUserDTO registrationUserDTO) {

        Employee employee = getEmployeeByEmail(registrationUserDTO.getEmail());
        employee.setName(registrationUserDTO.getName());
        employee.setPassword(passwordEncoder.encode(registrationUserDTO.getPassword()));

        return employeeRepository.save(employee);
    }

    @Override
    public Employee createAdmin(Company company, RegistrationUserDTO registrationAdminDTO) {

        if (employeeRepository.findByEmail(registrationAdminDTO.getEmail()).isPresent()) {
            log.error("This user already exist: {}", registrationAdminDTO.getName());
            throw new UserAlreadyExistException("This user already exist: " + registrationAdminDTO.getName());
        }

        Role role = roleService.getRoleByNameAndCompany("ROLE_ADMIN", company.getCompanyId());

        Employee admin = employeeMapper.registrationUserDTOToEmployee(registrationAdminDTO);

        admin.setPassword(passwordEncoder.encode(registrationAdminDTO.getPassword()));
        admin.setCompanyId(company.getCompanyId());
        admin.setRoles(List.of(role));
        admin.setCuratorId(0L);

        employeeRepository.save(admin);

        log.info("[createUser] << result is ROLE_MANAGER");

        return admin;
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Employee employee = getEmployeeByEmail(email);

        if (employee == null) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }

        log.info("[loadUserByUsername] << JwtEmployee with email: {} successfully loaded", email);

        return new org.springframework.security.core.userdetails.User(
                employee.getEmail(),
                employee.getPassword(),
                employee.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }
}
