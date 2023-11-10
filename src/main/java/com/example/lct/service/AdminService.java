package com.example.lct.service;

import com.example.lct.model.*;
import com.example.lct.web.dto.request.admin.*;
import com.example.lct.web.dto.request.admin.obj.ArticleDTO;
import com.example.lct.web.dto.request.admin.obj.AudioDTO;
import com.example.lct.web.dto.request.admin.obj.VideoDTO;
import com.example.lct.web.dto.response.CompanyAndJwtResponseDTO;

import java.util.List;

public interface AdminService {
    CompanyAndJwtResponseDTO createCompany(RegistrationCompanyDTO registrationCompanyDTO);

    List<Department> createDepartmentsToCompany(Company companyByUserPrincipal, DepartmentsDTO departmentsDTO);

    List<Post> createPostsToCompany(Company company, PostsDTO postsDTO);

    List<Employee> createEmployeesToCompany(Company companyByUserPrincipal, EmployeeListForCreateDTO employees);

    List<Question> createQuestionToCompany(Company companyByUserPrincipal, QuestionsDTO questionsDTO);

    List<Product> createProductToCompany(Company companyByUserPrincipal, ProductsDTO productDTO);

    List<Article> createArticleListToCompany(Company companyByUserPrincipal, ArticlesDTO articlesDTO);

    List<Article> createArticleToCompany(Company companyByUserPrincipal, ArticleDTO articleDTO);

    List<Video> createVideoListToCompany(Company companyByUserPrincipal, VideosDTO videosDTO);

    List<Video> createVideoToCompany(Company companyByUserPrincipal, VideoDTO videoDTO);

    List<Audio> createAudioListToCompany(Company companyByUserPrincipal, AudiosDTO audiosDTO);

    List<Audio> createAudioToCompany(Company companyByUserPrincipal, AudioDTO audioDTO);
}
