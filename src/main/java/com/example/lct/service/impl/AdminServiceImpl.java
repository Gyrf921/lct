package com.example.lct.service.impl;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.model.KnowledgeBase;
import com.example.lct.model.Post;
import com.example.lct.model.status.Department;
import com.example.lct.model.status.Product;
import com.example.lct.model.status.Question;
import com.example.lct.repository.CompanyRepository;
import com.example.lct.service.AdminService;
import com.example.lct.service.CompanyService;
import com.example.lct.service.EmployeeService;
import com.example.lct.web.dto.request.admin.*;
import com.example.lct.web.dto.response.CompanyAndJwtResponseDTO;
import com.example.lct.web.dto.response.JwtResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final EmployeeService employeeService;
    private final DepartmentServiceImpl departmentService;
    private final PostServiceImpl postService;
    private final QuestionServiceImpl questionService;
    private final ProductServiceImpl productService;
    private final KnowledgeBaseServiceImpl knowledgeBaseService;
    private final CompanyService companyService;
    //private final CompanyRepository companyRepository;


    @Override
    @Transactional
    public CompanyAndJwtResponseDTO createCompany(RegistrationCompanyDTO registrationCompanyDTO) {
        log.info("[createCompany] >> registrationCompanyDTO: {}", registrationCompanyDTO);

        //TODO разделяем логику и используем метод выше как фасад + переименовать
        Company company = companyService.createCompany(registrationCompanyDTO.getCompanyDTO());

        Employee employee = employeeService.createAdmin(company, registrationCompanyDTO.getRegistrationAdminDTO());

        companyService.setAdmin(company, employee);

        String token = employeeService.createTokenForUser(employee.getEmail());

        CompanyAndJwtResponseDTO companyAndJwtResponseDTO =
                new CompanyAndJwtResponseDTO(registrationCompanyDTO.getCompanyDTO(), new JwtResponseDTO(token));

        log.info("[createCompany] << result: {}", companyAndJwtResponseDTO);

        return companyAndJwtResponseDTO;
    }



    @Override
    public List<Department> createDepartmentsToCompany(Company company, DepartmentsDTO departmentsDTO) {

        List<Department> departments = departmentService.saveAllDepartmentForCompany(company.getCompanyId(), departmentsDTO);

        company.setDepartments(departments);

        Company savedCompany = companyService.saveCompany(company);

        log.info("[createDepartmentsToCompany] << result: {}", savedCompany);
        return departments;
    }

    @Override
    public List<Post> createPostsToCompany(Company company, PostsDTO postsDTO) {

        List<Post> posts = postService.saveAllPostForCompany(company.getCompanyId(), postsDTO);

        company.setPosts(posts);

        Company savedCompany = companyService.saveCompany(company);

        log.info("[createPostsToCompany] << result: {}", savedCompany);
        return posts;
    }

    @Override
    public List<Employee> createEmployeesToCompany(Company company, EmployeesDTO employeesDTO) {

        List<Employee> employees = employeeService.createEmployeesByAdmin(company.getCompanyId(), employeesDTO);

        log.info("[createEmployeesToCompany] << result employees: {}", employees);
        company.setEmployees(employees);

        Company savedCompany = companyService.saveCompany(company);
        log.info("[createEmployeesToCompany] << result company: {}", savedCompany);

        return employees;
    }

    @Override
    public List<Question> createQuestionToCompany(Company company, QuestionsDTO questionsDTO) {

        List<Question> questions = questionService.saveAllQuestionForCompany(company.getCompanyId(), questionsDTO);

        company.setQuestions(questions);

        Company savedCompany = companyService.saveCompany(company);

        log.info("[createQuestionToCompany] << result: {}", savedCompany);

        return questions;
    }

    @Override
    public List<Product> createProductToCompany(Company company, ProductsDTO productsDTO) {

        List<Product> products = productService.saveAllProductsForCompany(company.getCompanyId(), productsDTO);

        company.setProducts(products);

        Company savedCompany = companyService.saveCompany(company);

        log.info("[createProductToCompany] << result: {}", savedCompany);

        return products;
    }

    @Override
    @Transactional
    public List<KnowledgeBase> createKnowledgeBaseToCompany(Company company, KnowledgeBaseDTO knowledgeBaseDTO) {

        KnowledgeBase knowledgeBase = knowledgeBaseService.saveKnowledgeBaseForCompany(company, knowledgeBaseDTO);

        List<KnowledgeBase> knowledgeBases;

        if (company.getKnowledgeBases() == null || company.getKnowledgeBases().isEmpty()){
            knowledgeBases = new ArrayList<>();
            knowledgeBases.add(knowledgeBase);
        }
        else {
            knowledgeBases = company.getKnowledgeBases();
            knowledgeBases.add(knowledgeBase);
        }

        company.setKnowledgeBases(knowledgeBases);

        companyService.saveCompany(company);

        log.info("[createKnowledgeBaseToCompany] << result: {}", knowledgeBases);

        return knowledgeBases;
    }
}
