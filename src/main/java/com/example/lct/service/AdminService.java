package com.example.lct.service;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.model.KnowledgeBase;
import com.example.lct.model.Post;
import com.example.lct.model.status.Department;
import com.example.lct.model.status.Product;
import com.example.lct.model.status.Question;
import com.example.lct.web.dto.request.admin.*;
import com.example.lct.web.dto.response.CompanyAndJwtResponseDTO;

import java.util.List;

public interface AdminService {
    CompanyAndJwtResponseDTO createCompany(RegistrationCompanyDTO registrationCompanyDTO);

    List<Department> createDepartmentsToCompany(Company companyByUserPrincipal, DepartmentsDTO departmentsDTO);

    List<Post> createPostsToCompany(Company company, PostsDTO postsDTO);

    List<Employee> createEmployeesToCompany(Company companyByUserPrincipal, EmployeesDTO employees);

    List<Question> createQuestionToCompany(Company companyByUserPrincipal, QuestionsDTO questionsDTO);

    List<Product> createProductToCompany(Company companyByUserPrincipal, ProductsDTO productDTO);

    List<KnowledgeBase> createKnowledgeBaseToCompany(Company companyByUserPrincipal, KnowledgeBaseDTO knowledgeBaseDTO);


}
