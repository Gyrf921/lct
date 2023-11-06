package com.example.lct.web.controller;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.model.KnowledgeBase;
import com.example.lct.model.status.Department;
import com.example.lct.model.status.Product;
import com.example.lct.model.status.Question;
import com.example.lct.service.AdminService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.admin.*;
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
@RequestMapping("/admin/company")
public class AdminController {

    private final AdminService adminService;
    private final UserPrincipalUtils userPrincipalUtils;

    @Operation(summary = "get company for admin")
    @GetMapping
    public ResponseEntity<Company> createDepartmentsToCompany(Principal principal) {
        return ResponseEntity.ok().body(userPrincipalUtils.getCompanyByUserPrincipal(principal));
    }

    @Operation(summary = "add list of Departments To Company")
    @PostMapping("/departments")
    public ResponseEntity<List<Department>> createDepartmentsToCompany(Principal principal, @RequestBody DepartmentsDTO departmentsDTO) {

        List<Department> savedDepartment = adminService.createDepartmentsToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), departmentsDTO);

        return ResponseEntity.ok().body(savedDepartment);
    }

    @Operation(summary = "add list of Employees To Company")
    @PostMapping("/employees")
    public ResponseEntity<List<Employee>> createEmployeesToCompany(Principal principal, @RequestBody EmployeesDTO employees) {

        List<Employee> savedEmployee = adminService.createEmployeesToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), employees);

        return ResponseEntity.ok().body(savedEmployee);
    }

    @Operation(summary = "add one Question To Company")
    @PostMapping("/questions")
    public ResponseEntity<List<Question>> createQuestionsToCompany(Principal principal, @RequestBody QuestionsDTO questionsDTO) {

        List<Question> savedQuestions = adminService.createQuestionToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), questionsDTO);

        return ResponseEntity.ok().body(savedQuestions);
    }

    @Operation(summary = "add one product in shop for Company")
    @PostMapping("/products")
    public ResponseEntity<List<Product>> createProductsToCompany(Principal principal, @RequestBody ProductsDTO productsDTO) {

        List<Product> savedProducts = adminService.createProductToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), productsDTO);

        return ResponseEntity.ok().body(savedProducts);
    }

    @Operation(summary = "add KnowledgeBase with Knowledge to Company")
    @PostMapping("/knowledge-base")
    public ResponseEntity<List<KnowledgeBase>> createKnowledgeBaseToCompany(Principal principal, @RequestBody KnowledgeBaseDTO knowledgeBaseDTO) {

        List<KnowledgeBase> namesKnowledgeList = adminService.createKnowledgeBaseToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), knowledgeBaseDTO);

        return ResponseEntity.ok().body(namesKnowledgeList);
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
