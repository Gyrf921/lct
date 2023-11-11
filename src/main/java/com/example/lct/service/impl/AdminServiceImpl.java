package com.example.lct.service.impl;

import com.example.lct.mapper.KnowledgeMapper;
import com.example.lct.model.*;
import com.example.lct.service.AdminService;
import com.example.lct.service.CompanyService;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.TaskService;
import com.example.lct.web.dto.request.admin.*;
import com.example.lct.web.dto.request.admin.obj.ArticleDTO;
import com.example.lct.web.dto.request.admin.obj.AudioDTO;
import com.example.lct.web.dto.request.admin.obj.QuestionDTO;
import com.example.lct.web.dto.request.admin.obj.VideoDTO;
import com.example.lct.web.dto.response.CompanyAndJwtResponseDTO;
import com.example.lct.web.dto.response.obj.ArticleResponseDTO;
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
    private final CompanyService companyService;
    private final TaskService taskService;

    private final ArticleServiceImpl articleService;
    private final VideoServiceImpl videoService;
    private final AudioServiceImpl audioService;

    private final KnowledgeMapper knowledgeMapper;


    @Override
    @Transactional
    public CompanyAndJwtResponseDTO createCompany(RegistrationCompanyDTO registrationCompanyDTO) {
        log.info("[createCompany] >> registrationCompanyDTO: {}", registrationCompanyDTO);

        Company company = companyService.createCompany(registrationCompanyDTO.getCompanyDTO());

        Employee employee = employeeService.createAdmin(company, registrationCompanyDTO.getRegistrationAdminDTO());

        companyService.saveAdmin(company, employee);

        JwtResponseDTO token = employeeService.createTokenForUser(employee.getEmail());

        CompanyAndJwtResponseDTO companyAndJwtResponseDTO =
                new CompanyAndJwtResponseDTO(registrationCompanyDTO.getCompanyDTO(), token);

        log.info("[createCompany] << result: {}", companyAndJwtResponseDTO);

        return companyAndJwtResponseDTO;
    }

    @Override
    public List<Department> createDepartmentsToCompany(Company company, DepartmentsDTO departmentsDTO) {

        List<Department> departments = departmentService.saveAllDepartmentForCompany(company.getCompanyId(), departmentsDTO);

        List<Department> totalDepartments;
        if (company.getDepartments() == null || company.getDepartments().isEmpty()) {
            totalDepartments = new ArrayList<>(departments);
        } else {
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
        if (company.getPosts() == null || company.getPosts().isEmpty()) {
            totalPosts = new ArrayList<>(posts);
        } else {
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
        if (company.getEmployees() == null || company.getEmployees().isEmpty()) {
            totalEmployees = new ArrayList<>(employees);
        } else {
            totalEmployees = company.getEmployees();
            totalEmployees.addAll(employees);
        }

        company.setEmployees(totalEmployees);

        Company savedCompany = companyService.saveCompany(company);
        log.info("[createEmployeesToCompany] << result company: {}", savedCompany);

        return employees;
    }

    @Override
    public List<Question> createQuestionsToCompany(Company company, QuestionsDTO questionsDTO) {
        List<Question> questions = questionService.saveAllQuestionForCompany(company.getCompanyId(), questionsDTO);

        List<Question> totalQuestions;

        if (company.getQuestions() == null || company.getQuestions().isEmpty()) {
            totalQuestions = new ArrayList<>(questions);
        } else {
            totalQuestions = company.getQuestions();
            totalQuestions.addAll(questions);
        }

        company.setQuestions(totalQuestions);

        Company savedCompany = companyService.saveCompany(company);

        log.info("[createQuestionToCompany] << result: {}", savedCompany);

        return questions;
    }
    @Override
    public List<ArticleResponseDTO> createQuestionToCompany(Company company, QuestionDTO questionDTO) {

        Question question = questionService.saveQuestionForCompany(company.getCompanyId(), questionDTO);

        List<Question> totalQuestions;

        if (company.getQuestions() == null || company.getQuestions().isEmpty()) {
            totalQuestions = new ArrayList<>(List.of(question));
        } else {
            totalQuestions = company.getQuestions();
            totalQuestions.add(question);
        }

        company.setQuestions(totalQuestions);

        Company savedCompany = companyService.saveCompany(company);

        log.info("[createQuestionToCompany] << result: {}", savedCompany);

        return savedCompany.getQuestions().stream().map(knowledgeMapper::questionToArticleResponseDTO).toList();
    }

    @Override
    public Question updateQuestion(Long questionId, QuestionDTO questionDTO) {
        Question question =  questionService.getQuestionById(questionId);

        question.setImagePath(questionDTO.getImagePath());
        question.setTheme(questionDTO.getTheme());
        question.setAnswer(questionDTO.getAnswer());

        return questionService.saveQuestion(question);
    }

    @Override
    public void deleteQuestion(Company company, Long questionId) {
        Question question = questionService.getQuestionById(questionId);

        company.getQuestions().remove(question);

        company = companyService.saveCompany(company);

        questionService.deleteQuestion(question);
    }

    @Override
    public List<Product> createProductToCompany(Company company, ProductsDTO productsDTO) {

        List<Product> productsSaved = productService.saveAllProductsForCompany(company.getCompanyId(), productsDTO);

        List<Product> products;
        if (company.getProducts() == null || company.getProducts().isEmpty()) {
            products = new ArrayList<>(productsSaved);
        } else {
            products = company.getProducts();
            products.addAll(productsSaved);
        }

        company.setProducts(products);

        Company savedCompany = companyService.saveCompany(company);

        log.info("[createProductToCompany] << result: {}", savedCompany);

        return products;
    }

    @Override
    public List<Article> createArticleListToCompany(Company companyByUserPrincipal, ArticlesDTO articlesDTO) {

        List<Article> articlesSaved = articleService.createArticles(companyByUserPrincipal.getCompanyId(), articlesDTO);

        List<Article> articles;
        if (companyByUserPrincipal.getArticles() == null || companyByUserPrincipal.getArticles().isEmpty()) {
            articles = new ArrayList<>(articlesSaved);
        } else {
            articles = companyByUserPrincipal.getArticles();
            articles.addAll(articlesSaved);
        }

        companyByUserPrincipal.setArticles(articles);

        companyService.saveCompany(companyByUserPrincipal);

        return articles;
    }

    @Override
    public List<ArticleResponseDTO> createArticleToCompany(Company companyByUserPrincipal, ArticleDTO articleDTO) {
        Article articleSaved = articleService.createArticle(companyByUserPrincipal.getCompanyId(), articleDTO);

        List<Article> articles;
        if (companyByUserPrincipal.getArticles() == null || companyByUserPrincipal.getArticles().isEmpty()) {
            articles = new ArrayList<>(List.of(articleSaved));
        } else {
            articles = companyByUserPrincipal.getArticles();
            articles.add(articleSaved);
        }

        companyByUserPrincipal.setArticles(articles);

        Company company = companyService.saveCompany(companyByUserPrincipal);

        return company.getArticles().stream().map(knowledgeMapper::articleToArticleResponseDTO).toList();
    }
    @Override
    public Article updateArticle(Long articleId, ArticleDTO articleDTO) {
        Article article = articleService.getArticleById(articleId);
        Post post = postService.getPostByNameAndCompanyId(article.getCompanyId(), articleDTO.getPostName());

        article.setPost(post);
        article.setDepartment(post.getDepartment());
        article.setImagePath(articleDTO.getImagePath());
        article.setTheme(articleDTO.getTheme());
        article.setInformation(articleDTO.getInformation());

        articleService.saveArticle(article);

        return article;
    }

    @Override
    public List<Article> deleteArticle(Company company, Long articleId) {

        Article article = articleService.getArticleById(articleId);
        company.getArticles().remove(article);

        for(Employee employee :company.getEmployees()){
            employee.getFavoriteArticles().remove(article);
        }

        company = companyService.saveCompany(company);

        articleService.deleteArticle(article);

        return company.getArticles();
    }

    @Override
    public List<Task> deleteTask(Company company, Long taskId) {
        Task task = taskService.getTaskById(taskId);
        taskService.deleteTask(task);
        company.getTasks().remove(task);
        companyService.saveCompany(company);
        return company.getTasks();
    }

    @Override
    public Employee getEmployee(Long id) {
        return employeeService.getEmployeeById(id);
    }

    @Override
    public List<Video> createVideoListToCompany(Company companyByUserPrincipal, VideosDTO videosDTO) {
        List<Video> videosSaved = videoService.createVideos(companyByUserPrincipal.getCompanyId(), videosDTO);

        List<Video> videos;
        if (companyByUserPrincipal.getVideos() == null || companyByUserPrincipal.getVideos().isEmpty()) {
            videos = new ArrayList<>(videosSaved);
        } else {
            videos = companyByUserPrincipal.getVideos();
            videos.addAll(videosSaved);
        }

        companyByUserPrincipal.setVideos(videos);

        companyService.saveCompany(companyByUserPrincipal);

        return videos;
    }

    @Override
    public List<Video> createVideoToCompany(Company companyByUserPrincipal, VideoDTO videoDTO) {
        Video videoSaved = videoService.createVideo(companyByUserPrincipal.getCompanyId(), videoDTO);

        List<Video> videos;
        if (companyByUserPrincipal.getVideos() == null || companyByUserPrincipal.getVideos().isEmpty()) {
            videos = new ArrayList<>(List.of(videoSaved));
        } else {
            videos = companyByUserPrincipal.getVideos();
            videos.add(videoSaved);
        }

        companyByUserPrincipal.setVideos(videos);

        Company company = companyService.saveCompany(companyByUserPrincipal);

        return company.getVideos();
    }

    @Override
    public List<Audio> createAudioListToCompany(Company companyByUserPrincipal, AudiosDTO audiosDTO) {
        List<Audio> audiosSaved = audioService.createAudios(companyByUserPrincipal.getCompanyId(), audiosDTO);

        List<Audio> audios;
        if (companyByUserPrincipal.getAudio() == null || companyByUserPrincipal.getAudio().isEmpty()) {
            audios = new ArrayList<>(audiosSaved);
        } else {
            audios = companyByUserPrincipal.getAudio();
            audios.addAll(audiosSaved);
        }

        companyByUserPrincipal.setAudio(audios);

        companyService.saveCompany(companyByUserPrincipal);

        return audios;
    }

    @Override
    public List<Audio> createAudioToCompany(Company companyByUserPrincipal, AudioDTO audioDTO) {
        Audio audioSaved = audioService.createAudio(companyByUserPrincipal.getCompanyId(), audioDTO);

        List<Audio> audios;
        if (companyByUserPrincipal.getAudio() == null || companyByUserPrincipal.getAudio().isEmpty()) {
            audios = new ArrayList<>(List.of(audioSaved));
        } else {
            audios = companyByUserPrincipal.getAudio();
            audios.add(audioSaved);
        }

        companyByUserPrincipal.setAudio(audios);

        Company company =companyService.saveCompany(companyByUserPrincipal);

        return company.getAudio();
    }




}
