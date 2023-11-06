package com.example.lct.web.controller.notyet;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.repository.CompanyRepository;
import com.example.lct.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    @GetMapping("/unsecure")
    public String unsecureData() {
        return "unsecure Data (don't need authorization)";
    }

    @GetMapping("/secure")
    public String secureData() {
        return "secure Data for user and admin (need authorization)";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "admin Data only for admin";
    }


    @Operation(summary = "Set setting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/start")
    public ResponseEntity<?> setStartSetting() {
        log.info("[setStartSetting] >> getAllCompany");

/*

        Employee employee = employeeRepository.save(Employee.builder().name("1").build());
        Employee employee2 = employeeRepository.save(Employee.builder().name("2").build());
        Employee employee3 = employeeRepository.save(Employee.builder().name("3").build());
        Employee employee4 = employeeRepository.save(Employee.builder().name("4").build());


        Company company1 = companyRepository.save(Company.builder()
                .name("Name1").employees(List.of(employee, employee2, employee3, employee4)).build());

*/


/*        Company company2 = companyRepository.save(Company.builder()
                .name("Name2").build());
        Company company3 = companyRepository.save(Company.builder()
                .name("Name3").build());*/



/*        HashSet<Company> emp1 = new HashSet<>();
        emp1.add(company1);
        emp1.add(company2);
        HashSet<Company> emp2 = new HashSet<>();
        emp2.add(company3);
        emp2.add(company2);

        employee.setCompanies(emp1);
        employee2.setCompanies(emp2);*/
/*        employee.getCompanies().add(company1);
        employee.getCompanies().add(company2);*/


/*        HashSet<Employee> comp1 = new HashSet<>();
        comp1.add(employee);
        company1.setEmployees(comp1);*/
        /*HashSet<Employee> comp2 = new HashSet<>();
        comp2.add(employee);
        comp2.add(employee2);
        HashSet<Employee> comp3 = new HashSet<>();
        comp3.add(employee2);

        company2.setEmployees(comp2);
        company3.setEmployees(comp3);*/


/*        company1.setEmployees(Set.of(employee));
        company2.setEmployees(Set.of(employee, employee2));
        company3.setEmployees(Set.of(employee2));   */


/*        Employee employee1 = Employee.builder().name("2").build();
        Employee employee2 = Employee.builder().name("3").build();
        Employee employee3 = Employee.builder().name("4").build();*/

        log.info("[setStartSetting] << result: {}", true);

        return ResponseEntity.ok().body(null);
    }

    @Operation(summary = "Get all company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompany() {

        List<Company> companies = companyRepository.findAll();

        return ResponseEntity.ok().body(companies);
    }

    @Operation(summary = "getAllEmployee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Validation failed for some argument. Invalid input supplied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/getAllEmployee")
    public ResponseEntity<?> getAllEmployee() {

        List<Employee> companies = employeeRepository.findAll();

        return ResponseEntity.ok().body(companies);
    }


}
