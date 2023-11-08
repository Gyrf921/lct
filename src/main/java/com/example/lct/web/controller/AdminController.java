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
@RequestMapping("/admin/company")
public class AdminController {

    private final AdminService adminService;
    private final UserPrincipalUtils userPrincipalUtils;

    @Operation(summary = "get company for admin")
    @GetMapping
    public ResponseEntity<Company> getDepartments(Principal principal) {
        return ResponseEntity.ok().body(userPrincipalUtils.getCompanyByUserPrincipal(principal));
    }

    @Operation(summary = "add list of Departments To Company")
    @PostMapping("/departments")
    public ResponseEntity<List<Department>> createDepartmentsToCompany(Principal principal, @RequestBody DepartmentsDTO departmentsDTO) {

        List<Department> savedDepartment = adminService.createDepartmentsToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), departmentsDTO);

        return ResponseEntity.ok().body(savedDepartment);
    }

    @Operation(summary = "add list of Posts To Company")
    @PostMapping("/posts")
    public ResponseEntity<List<Post>> createPostsToCompany(Principal principal, @RequestBody PostsDTO postsDTO) {

        List<Post> savedPosts = adminService.createPostsToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), postsDTO);

        return ResponseEntity.ok().body(savedPosts);
    }

    @Operation(summary = "add list of Employees To Company")
    @PostMapping("/employees")
    public ResponseEntity<List<Employee>> createEmployeesToCompany(Principal principal, @RequestBody EmployeeListForCreateDTO employees) {

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
    public ResponseEntity<List<Product>> createProductsToCompany(Principal principal,
                                                                 @RequestBody ProductsDTO productsDTO) {

        List<Product> savedProducts = adminService.createProductToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), productsDTO);

        return ResponseEntity.ok().body(savedProducts);
    }

    @Operation(summary = "add articles to Company")
    @PostMapping("/articles")
    public ResponseEntity<List<Article>> createArticleListToCompany(Principal principal, @RequestBody ArticlesDTO articlesDTO) {

        return ResponseEntity.ok().body(adminService.createArticleListToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), articlesDTO));
    }

    @Operation(summary = "add one article to Company")
    @PostMapping("/article")
    public ResponseEntity<Article> createArticleToCompany(Principal principal, @RequestBody ArticleDTO articleDTO) {

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
    public ResponseEntity<Video> createVideoToCompany(Principal principal, @RequestBody VideoDTO videoDTO) {

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
    public ResponseEntity<Audio> createAudioToCompany(Principal principal, @RequestBody AudioDTO audioDTO) {

        return ResponseEntity.ok().body(adminService.createAudioToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), audioDTO));
    }
/* TODO
добавление записей в базу знаний
методы для штучного добавления всего (в основе база знаний)
 * */
}
