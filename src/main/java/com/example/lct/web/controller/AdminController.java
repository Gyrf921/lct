package com.example.lct.web.controller;

import com.example.lct.model.*;
import com.example.lct.service.AdminService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.admin.*;
import com.example.lct.web.dto.request.admin.obj.ArticleDTO;
import com.example.lct.web.dto.request.admin.obj.AudioDTO;
import com.example.lct.web.dto.request.admin.obj.VideoDTO;
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
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserPrincipalUtils userPrincipalUtils;

    //region Company
    @Operation(summary = "get company for admin")
    @GetMapping("/company")
    public ResponseEntity<Company> getCompany(Principal principal) {
        log.info("[AdminController|getCompany] >> user principal: {}", principal.getName());

        Company company = userPrincipalUtils.getCompanyByUserPrincipal(principal);

        log.info("[AdminController|getCompany] << result has companyId: {}", company.getCompanyId());

        return ResponseEntity.ok().body(company);
    }
    @Operation(summary = "add list of Departments To Company")
    @PostMapping("/company/departments")
    public ResponseEntity<List<Department>> createDepartments(Principal principal,
                                                              @RequestBody DepartmentsDTO departmentsDTO) {
        log.info("[AdminController|createDepartments] >> user principal: {}", principal.getName());

        List<Department> savedDepartment = adminService.createDepartmentsToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), departmentsDTO);

        log.info("[AdminController|createDepartments] << result: {}", savedDepartment);

        return ResponseEntity.ok().body(savedDepartment);
    }

    @Operation(summary = "add list of Posts To Company")
    @PostMapping("/company/posts")
    public ResponseEntity<List<Post>> createPosts(Principal principal,
                                                           @RequestBody PostsDTO postsDTO) {
        log.info("[AdminController|createPosts] >> user principal: {}", principal.getName());

        List<Post> savedPosts = adminService.createPostsToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), postsDTO);

        log.info("[AdminController|createPosts] << result: {}", savedPosts);
        return ResponseEntity.ok().body(savedPosts);
    }

    @Operation(summary = "add one product in shop for Company")
    @PostMapping("/company/products")
    public ResponseEntity<List<Product>> createProductsToCompany(Principal principal,
                                                                 @RequestBody ProductsDTO productsDTO) {
        log.info("[AdminController|createPosts] >> user principal: {}", principal.getName());

        List<Product> savedProducts = adminService.createProductToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), productsDTO);

        log.info("[AdminController|createPosts] << result: {}", savedProducts);
        return ResponseEntity.ok().body(savedProducts);
    }
    //endregion

    @Operation(summary = "add list of Employees To Company")
    @PostMapping("/employees")
    public ResponseEntity<List<Employee>> createEmployeesToCompany(Principal principal, @RequestBody EmployeeListForCreateDTO employees) {

        List<Employee> savedEmployee = adminService.createEmployeesToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), employees);

        return ResponseEntity.ok().body(savedEmployee);
    }

    //region Knowledge base
    @Operation(summary = "add one Question To Company")
    @PostMapping("/questions")
    public ResponseEntity<List<Question>> createQuestionsToCompany(Principal principal, @RequestBody QuestionsDTO questionsDTO) {

        List<Question> savedQuestions = adminService.createQuestionToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), questionsDTO);

        return ResponseEntity.ok().body(savedQuestions);
    }



    @Operation(summary = "add articles to Company")
    @PostMapping("/articles")
    public ResponseEntity<List<Article>> createArticleListToCompany(Principal principal, @RequestBody ArticlesDTO articlesDTO) {

        return ResponseEntity.ok().body(adminService.createArticleListToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), articlesDTO));
    }

    @Operation(summary = "add one article to Company")
    @PostMapping("/article")
    public ResponseEntity<List<Article>> createArticleToCompany(Principal principal, @RequestBody ArticleDTO articleDTO) {

        return ResponseEntity.ok().body(adminService.createArticleToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), articleDTO));
    }

    @Operation(summary = "add videos to Company")
    @PostMapping("/videos")
    public ResponseEntity<List<Video>> createVideoListToCompany(Principal principal, @RequestBody VideosDTO videosDTO) {

        return ResponseEntity.ok().body(adminService.createVideoListToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), videosDTO));
    }

    @Operation(summary = "add one video to Company")
    @PostMapping("/video")
    public ResponseEntity<List<Video>> createVideoToCompany(Principal principal, @RequestBody VideoDTO videoDTO) {

        return ResponseEntity.ok().body(adminService.createVideoToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), videoDTO));
    }

    @Operation(summary = "add audios to Company")
    @PostMapping("/audios")
    public ResponseEntity<List<Audio>> createAudioListToCompany(Principal principal, @RequestBody AudiosDTO audiosDTO) {

        return ResponseEntity.ok().body(adminService.createAudioListToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), audiosDTO));
    }

    @Operation(summary = "add one audio to Company")
    @PostMapping("/audio")
    public ResponseEntity<List<Audio>> createAudioToCompany(Principal principal, @RequestBody AudioDTO audioDTO) {

        return ResponseEntity.ok().body(adminService.createAudioToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), audioDTO));
    }

    //endregion

}
