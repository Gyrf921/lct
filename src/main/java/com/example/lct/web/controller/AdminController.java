package com.example.lct.web.controller;

import com.example.lct.model.*;
import com.example.lct.service.AdminService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.admin.*;
import com.example.lct.web.dto.request.admin.obj.ArticleDTO;
import com.example.lct.web.dto.request.admin.obj.AudioDTO;
import com.example.lct.web.dto.request.admin.obj.QuestionDTO;
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

    //region Tasks
    @Operation(summary = "get all company tasks")
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks(Principal principal) {
        log.info("[AdminController|getTasks] >> user principal: {}", principal.getName());

        Company company = userPrincipalUtils.getCompanyByUserPrincipal(principal);

        log.info("[AdminController|getTasks] << result: {}", company.getTasks());

        return ResponseEntity.ok().body(company.getTasks());
    }

    @Operation(summary = "delete task by id")
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<List<Task>> deleteTask(@PathVariable(name = "taskId") Long taskId,
                                                 Principal principal) {
        log.info("[AdminController|deleteTask] >> taskId: {}, user principal: {}", taskId,  principal.getName());

        List<Task> tasks = adminService.deleteTask(userPrincipalUtils.getCompanyByUserPrincipal(principal), taskId);

        log.info("[AdminController|deleteTask] << result: {}", tasks);

        return ResponseEntity.ok().body(tasks);
    }
    //endregion

    //region Knowledge base
    @Operation(summary = "add questions To Company")
    @PostMapping("/knowledge-base/questions")
    public ResponseEntity<List<Question>> createQuestions(Principal principal,
                                                          @RequestBody QuestionsDTO questionsDTO) {
        log.info("[AdminController|createQuestions] >> questionsDTO: {}", questionsDTO);

        List<Question> savedQuestions = adminService.createQuestionsToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), questionsDTO);

        log.info("[AdminController|createQuestions] << result: {}", savedQuestions);

        return ResponseEntity.ok().body(savedQuestions);
    }

    @Operation(summary = "add one Question To Company")
    @PostMapping("/knowledge-base/question")
    public ResponseEntity<List<Question>> createQuestion(Principal principal,
                                                         @RequestBody QuestionDTO questionDTO) {
        log.info("[AdminController|createQuestion] >> questionDTO: {}", questionDTO);

        List<Question> savedQuestions = adminService.createQuestionToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), questionDTO);

        log.info("[AdminController|createQuestions] << result: {}", savedQuestions);
        return ResponseEntity.ok().body(savedQuestions);
    }

    @Operation(summary = "add articles to Company")
    @PostMapping("/knowledge-base/articles")
    public ResponseEntity<List<Article>> createArticles(Principal principal,
                                                           @RequestBody ArticlesDTO articlesDTO) {
        log.info("[AdminController|createArticles] >> articlesDTO: {}", articlesDTO);

        List<Article> articles = adminService.createArticleListToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), articlesDTO);

        log.info("[AdminController|createArticles] << result: {}", articlesDTO);
        return ResponseEntity.ok().body(articles);
    }

    @Operation(summary = "add one article to Company")
    @PostMapping("/knowledge-base/article")
    public ResponseEntity<List<Article>> createArticle(Principal principal,
                                                       @RequestBody ArticleDTO articleDTO) {
        log.info("[AdminController|createArticle] >> articlesDTO: {}", articleDTO);

        List<Article> articles = adminService.createArticleToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), articleDTO);

        log.info("[AdminController|createArticle] << result: {}", articles);
        return ResponseEntity.ok().body(articles);
    }

    @Operation(summary = "add videos to Company")
    @PostMapping("/knowledge-base/videos")
    public ResponseEntity<List<Video>> createVideos(Principal principal,
                                                       @RequestBody VideosDTO videosDTO) {
        log.info("[AdminController|createVideos] >> videosDTO: {}", videosDTO);

        List<Video> videos = adminService.createVideoListToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), videosDTO);

        log.info("[AdminController|createVideos] << result: {}", videosDTO);

        return ResponseEntity.ok().body(videos);

    }

    @Operation(summary = "add one video to Company")
    @PostMapping("/knowledge-base/video")
    public ResponseEntity<List<Video>> createVideo(Principal principal,
                                                   @RequestBody VideoDTO videoDTO) {
        log.info("[AdminController|createVideo] >> videoDTO: {}", videoDTO);

        List<Video> videos = adminService.createVideoToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), videoDTO);

        log.info("[AdminController|createVideo] << result: {}", videos);

        return ResponseEntity.ok().body(videos);
    }

    @Operation(summary = "add audios to Company")
    @PostMapping("/knowledge-base/audios")
    public ResponseEntity<List<Audio>> createAudios(Principal principal,
                                                                @RequestBody AudiosDTO audiosDTO) {
        log.info("[AdminController|createAudios] >> audiosDTO: {}", audiosDTO);

        List<Audio> audios = adminService.createAudioListToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), audiosDTO);

        log.info("[AdminController|createAudios] << result: {}", audios);

        return ResponseEntity.ok().body(audios);
    }

    @Operation(summary = "add one audio to Company")
    @PostMapping("/knowledge-base/audio")
    public ResponseEntity<List<Audio>> createAudio(Principal principal, @RequestBody AudioDTO audioDTO) {
        log.info("[AdminController|createAudio] >> audioDTO: {}", audioDTO);

        List<Audio> audios = adminService.createAudioToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), audioDTO);

        log.info("[AdminController|createAudio] << result: {}", audios);

        return ResponseEntity.ok().body(audios);
    }


    @Operation(summary = "update article by id")
    @PutMapping("/knowledge-base/articles/{articleId}")
    public ResponseEntity<List<Article>> updateArticle(@PathVariable(value = "articleId") Long articleId,
                                                       @RequestBody ArticleDTO articleDTO) {
        log.info("[AdminController|updateArticle] >> articleId: {}, articleDTO: {}", articleId, articleDTO);

        List<Article> articles = adminService.updateArticle(articleId, articleDTO);

        log.info("[AdminController|updateArticle] << result: {}", articleDTO);

        return ResponseEntity.ok().body(articles);
    }

    @Operation(summary = "delete article by id")
    @DeleteMapping("/knowledge-base/articles/{articleId}")
    public ResponseEntity<List<Article>> deleteArticle(@PathVariable(value = "articleId") Long articleId,Principal principal) {
        log.info("[AdminController|updateArticle] >> articleId: {}", articleId);

        List<Article> articles = adminService.deleteArticle(userPrincipalUtils.getCompanyByUserPrincipal(principal), articleId);

        log.info("[AdminController|updateArticle] << articleId: {}", articleId);

        return ResponseEntity.ok().body(articles);
    }
    //endregion

    //region Employee
    @Operation(summary = "add list of Employees To Company")
    @PostMapping("/employees")
    public ResponseEntity<List<Employee>> createEmployeesToCompany(Principal principal,
                                                                   @RequestBody EmployeeListForCreateDTO employees) {

        List<Employee> savedEmployee = adminService.createEmployeesToCompany(
                userPrincipalUtils.getCompanyByUserPrincipal(principal), employees);

        return ResponseEntity.ok().body(savedEmployee);
    }
    //endregion
}
