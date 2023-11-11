package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.exception.UserAlreadyExistException;
import com.example.lct.mapper.EmployeeMapper;
import com.example.lct.model.*;
import com.example.lct.model.enumformodel.HistoryType;
import com.example.lct.model.enumformodel.Status;
import com.example.lct.model.factory.EmployeeFactory;
import com.example.lct.repository.EmployeeRepository;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.HistoryService;
import com.example.lct.service.RoleService;
import com.example.lct.service.StageService;
import com.example.lct.util.JwtTokenUtils;
import com.example.lct.web.dto.request.EmployeePersonalityDTO;
import com.example.lct.web.dto.request.RegistrationUserDTO;
import com.example.lct.web.dto.request.admin.EmployeeListForCreateDTO;
import com.example.lct.web.dto.request.admin.FilterTeamDTO;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.response.EmployeePersonalityResponseDTO;
import com.example.lct.web.dto.response.EmployeeTeamResponseDTO;
import com.example.lct.web.dto.response.obj.JwtResponseDTO;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements UserDetailsService, EmployeeService {
    private final JwtTokenUtils jwtTokenUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    private final StageService stageService;
    private final RoleService roleService;
    private final DepartmentServiceImpl departmentService;
    private final PostServiceImpl postService;
    private final HistoryService historyService;

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
    public Employee getEmployeeById(Long id) {
        log.info("[getEmployeeById] >> email: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found by this id :{} ", id);
                    return new ResourceNotFoundException("Employee not found by this id :: " + id);
                });

        log.info("[getEmployeeById] << result: {}", employee.getName());

        return employee;
    }

    @Override
    public JwtResponseDTO createTokenForUser(String email) {
        log.info("[createTokenForUser] >> create token for email: {}", email);
        Employee employee = getEmployeeByEmail(email);
        historyService.createHistoryActionOther(employee, HistoryType.OTHER, "Вход в систему");
        UserDetails userDetails = loadUserByUsername(email);

        log.info("[createTokenForUser] << result is token");

        String token = jwtTokenUtils.generateToken(userDetails);

        return new JwtResponseDTO(token, employee.getRoles());
    }

    @Override
    public Employee createIntern(Long companyId, EmployeeForCreateDTO employeeForCreateDTO) {

        Employee intern = employeeRepository.save(
                EmployeeFactory.createEmployee(companyId, employeeForCreateDTO));

        List<Stage> stages = new ArrayList<>(List.of(stageService.createBaseStageForIntern(intern)));

        intern.setStages(stages);

        intern = employeeRepository.save(intern);
        log.info("[createIntern] << result : {}", intern);

        return intern;
    }

    @Override
    public List<EmployeeTeamResponseDTO> getAllInternByCuratorId(Long employeeIdByUserPrincipal) {

        List<Employee> interns = employeeRepository.findAllByCuratorId(employeeIdByUserPrincipal);

        log.info("[getAllInternByCuratorId] << result : {}", interns);

        return interns.stream().map(employeeMapper::employeeToTeamDTO).toList();
    }

    @Override
    public List<Employee> getAllIntern() {
        List<Role> rolesIntern = roleService.getRolesByName("ROLE_INTERN");

        List<Employee> interns = new ArrayList<>();
        for (Role role : rolesIntern) {
            interns.addAll(employeeRepository.findAllByRolesContaining(role));
        }

        log.info("[getAllIntern] << result : {}", interns);

        return interns;
    }

    @Override
    public List<Employee> createEmployeesByAdmin(Long companyId, EmployeeListForCreateDTO employeesByAdmin) {

        List<Employee> employeeSavedList = new ArrayList<>();

        for (EmployeeForCreateDTO employeeForCreateDTO : employeesByAdmin.getEmployeeForCreateDTOList()) {
            employeeSavedList.add(EmployeeFactory.createEmployee(companyId, employeeForCreateDTO));
        }

        List<Employee> savedEmployees = employeeRepository.saveAll(employeeSavedList);

        log.info("[createEmployeesByAdmin] << result : {}", savedEmployees.size());

        return savedEmployees;
    }

    @Override
    public Employee createEmployeeByHR(Company company, EmployeeForCreateDTO employeeForCreateDTO) {
        log.info("[EmployeeService|createEmployeesByAdmin] >> companyId: {}, employeeForCreateDTO: {}", company.getCompanyId(), employeeForCreateDTO);

        Employee employee = employeeRepository.save(
                EmployeeFactory.createEmployee(company.getCompanyId(), employeeForCreateDTO)
        );

        log.info("[EmployeeService|createEmployeesByAdmin] << result : {}", employee);

        return employee;
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
        log.info("[EmployeeService|createAdmin] >> companyId: {}, registrationAdminDTO: {}", company.getCompanyId(), registrationAdminDTO);

        if (employeeRepository.findByEmail(registrationAdminDTO.getEmail()).isPresent()) {
            log.error("This user already exist: {}", registrationAdminDTO.getName());
            throw new UserAlreadyExistException("This user already exist: " + registrationAdminDTO.getName());
        }

        Role role = roleService.getRoleByNameAndCompany(company.getCompanyId(), "ROLE_ADMIN");
        Post post = postService.getPostByNameAndCompanyId(company.getCompanyId(), "none");
        Employee admin = employeeMapper.registrationUserDTOToEmployee(registrationAdminDTO);

        admin.setPassword(passwordEncoder.encode(registrationAdminDTO.getPassword()));
        admin.setPost(post);
        admin.setCompanyId(company.getCompanyId());
        admin.setRoles(new ArrayList<>(List.of(role)));

        employeeRepository.save(admin);

        log.info("[EmployeeService|createUser] << result is admin");

        return admin;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        log.info("[EmployeeService|saveEmployee] == result is save employee: {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeePersonalityResponseDTO getEmployeeInformation(Employee employee) {
        EmployeePersonalityResponseDTO employeePersonalityResponseDTO =
                employeeMapper.employeeToEmployeePersonalityDTO(employee);

        List<TaskStage> taskStages = stageService.getAllTaskStageForEmployee(employee).stream()
                .filter(taskStage -> taskStage.getStatus().equals(Status.ACCEPTED)).toList();
        employeePersonalityResponseDTO.setCountCompletedTask(taskStages.size());
        return employeePersonalityResponseDTO;
    }

    @Override
    public EmployeePersonalityResponseDTO setEmployeeInformation(Employee employeeByUserPrincipal, EmployeePersonalityDTO employeePersonalityDTO) {
        Employee employee = saveEmployee(EmployeeFactory.updateEmployeeInfo(employeeByUserPrincipal, employeePersonalityDTO));
        return employeeMapper.employeeToEmployeePersonalityDTO(employee);
    }

    @Override
    public List<EmployeeTeamResponseDTO> getTeamWithFilter(Company companyByUserPrincipal, FilterTeamDTO filterTeamDTO) {
        List<Employee> employees = companyByUserPrincipal.getEmployees();

        if (filterTeamDTO.getPostName() != null) {
            Post post = postService.getPostByNameAndCompanyId(companyByUserPrincipal.getCompanyId(), filterTeamDTO.getPostName());
            employees = employees.stream().filter(employee -> employee.getPost().equals(post)).toList();
        }
        if (filterTeamDTO.getDepartmentName() != null) {
            Department department = departmentService.getDepartmentByNameAndCompanyId(companyByUserPrincipal.getCompanyId(), filterTeamDTO.getDepartmentName());
            employees = employees.stream().filter(employee -> employee.getPost().getDepartment().equals(department)).toList();
        }
        if (filterTeamDTO.getCityName() != null) {
            employees = employees.stream().filter(employee -> employee.getCity().equals(filterTeamDTO.getCityName())).toList();
        }
        if (filterTeamDTO.getEmployeeName() != null) {
            employees = employees.stream().filter(employee -> employee.getName().equals(filterTeamDTO.getEmployeeName())).toList();
        }

        return employees.stream().map(employeeMapper::employeeToTeamDTO).toList();
    }

    @Override
    public List<EmployeeTeamResponseDTO> getTeam(Company company) {
        return company.getEmployees().stream().map(employeeMapper::employeeToTeamDTO).toList();
    }


    @Override
    public List<Employee> getInternsByCuratorId(Long curatorId) {
        return employeeRepository.findAllByCuratorId(curatorId);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Employee employee = getEmployeeByEmail(email);

        if (employee == null) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }

        historyService.createHistoryActionOther(employee, HistoryType.OTHER, "Вход в систему");

        log.info("[loadUserByUsername] << JwtEmployee with email: {} successfully loaded", email);

        return new org.springframework.security.core.userdetails.User(
                employee.getEmail(),
                employee.getPassword(),
                employee.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList());
    }
}
