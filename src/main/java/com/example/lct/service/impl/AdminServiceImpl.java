package com.example.lct.service.impl;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.model.KnowledgeBase;
import com.example.lct.model.Post;
import com.example.lct.model.Department;
import com.example.lct.model.Product;
import com.example.lct.model.Question;
import com.example.lct.service.AdminService;
import com.example.lct.service.CompanyService;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.HistoryService;
import com.example.lct.web.dto.request.admin.*;
import com.example.lct.web.dto.response.CompanyAndJwtResponseDTO;
import com.example.lct.web.dto.response.obj.JwtResponseDTO;
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


    @Override
    @Transactional
    public CompanyAndJwtResponseDTO createCompany(RegistrationCompanyDTO registrationCompanyDTO) {
        log.info("[createCompany] >> registrationCompanyDTO: {}", registrationCompanyDTO);

        Company company = companyService.createCompany(registrationCompanyDTO.getCompanyDTO());

        Employee employee = employeeService.createAdmin(company, registrationCompanyDTO.getRegistrationAdminDTO());

        companyService.saveAdmin(company, employee);

        String token = employeeService.createTokenForUser(employee.getEmail());

        CompanyAndJwtResponseDTO companyAndJwtResponseDTO =
                new CompanyAndJwtResponseDTO(registrationCompanyDTO.getCompanyDTO(), new JwtResponseDTO(token));

        log.info("[createCompany] << result: {}", companyAndJwtResponseDTO);

        return companyAndJwtResponseDTO;
    }

    @Override
    public List<Department> createDepartmentsToCompany(Company company, DepartmentsDTO departmentsDTO) {

        List<Department> departments = departmentService.saveAllDepartmentForCompany(company.getCompanyId(), departmentsDTO);

        List<Department> totalDepartments;
        if (company.getDepartments() == null || company.getDepartments().isEmpty()){
            totalDepartments = new ArrayList<>(departments);
        }
        else{
            totalDepartments = company.getDepartments();
            totalDepartments.addAll(departments);
        }

        company.setDepartments(totalDepartments);

        Company savedCompany = companyService.saveCompany(company);

        log.info("[createDepartmentsToCompany] << result: {}", savedCompany);
        return departments;
    }

    @Override
    public List<Post> createPostsToCompany(Company company, PostsDTO postsDTO) {

        List<Post> posts = postService.saveAllPostForCompany(company.getCompanyId(), postsDTO);

        List<Post> totalPosts;
        if (company.getPosts() == null || company.getPosts().isEmpty()){
            totalPosts = new ArrayList<>(posts);
        }
        else{
            totalPosts = company.getPosts();
            totalPosts.addAll(posts);
        }
        company.setPosts(totalPosts);

        Company savedCompany = companyService.saveCompany(company);

        log.info("[createPostsToCompany] << result: {}", savedCompany);
        return posts;
    }

    @Override
    public List<Employee> createEmployeesToCompany(Company company, EmployeeListForCreateDTO employeeListForCreateDTO) {
        List<Employee> employees = employeeService.createEmployeesByAdmin(company.getCompanyId(), employeeListForCreateDTO);

        List<Employee> totalEmployees;
        if (company.getEmployees() == null || company.getEmployees().isEmpty()){
            totalEmployees = new ArrayList<>(employees);
        }
        else{
            totalEmployees = company.getEmployees();
            totalEmployees.addAll(employees);
        }

        company.setEmployees(totalEmployees);

        Company savedCompany = companyService.saveCompany(company);
        log.info("[createEmployeesToCompany] << result company: {}", savedCompany);

        return employees;
    }

    @Override
    public List<Question> createQuestionToCompany(Company company, QuestionsDTO questionsDTO) {

        List<Question> questions = questionService.saveAllQuestionForCompany(company.getCompanyId(), questionsDTO);

        List<Question> totalQuestions;

        if (company.getQuestions() == null || company.getQuestions().isEmpty()){
            totalQuestions = new ArrayList<>(questions);
        }
        else{
            totalQuestions = company.getQuestions();
            totalQuestions.addAll(questions);
        }

        company.setQuestions(totalQuestions);

        Company savedCompany = companyService.saveCompany(company);

        log.info("[createQuestionToCompany] << result: {}", savedCompany);

        return questions;
    }

    @Override
    public List<Product> createProductToCompany(Company company, ProductsDTO productsDTO) {

        List<Product> productsSaved = productService.saveAllProductsForCompany(company.getCompanyId(), productsDTO);

        List<Product> products;

        if (company.getProducts() == null || company.getProducts().isEmpty()){
            products = new ArrayList<>(productsSaved);
        }
        else{
            products = company.getProducts();
            products.addAll(productsSaved);
        }

        company.setProducts(products);

        Company savedCompany = companyService.saveCompany(company);

        log.info("[createProductToCompany] << result: {}", savedCompany);

        return products;
    }

    @Override
    @Transactional
    public List<KnowledgeBase> createKnowledgeBaseToCompany(Company company, KnowledgeBaseDTO knowledgeBaseDTO) {

        KnowledgeBase knowledgeBase = knowledgeBaseService.createKnowledgeBaseForCompany(company, knowledgeBaseDTO);

        List<KnowledgeBase> knowledgeBases;
        if (company.getKnowledgeBases() == null || company.getKnowledgeBases().isEmpty()){
            knowledgeBases = new ArrayList<>(List.of(knowledgeBase));
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
