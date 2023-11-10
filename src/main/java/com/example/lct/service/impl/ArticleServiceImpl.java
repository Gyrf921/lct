package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.model.Article;
import com.example.lct.model.Employee;
import com.example.lct.model.Post;
import com.example.lct.repository.ArticleRepository;
import com.example.lct.service.EmployeeService;
import com.example.lct.web.dto.request.admin.ArticlesDTO;
import com.example.lct.web.dto.request.admin.obj.ArticleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleServiceImpl {
    private final EmployeeService employeeService;
    private final ArticleRepository articleRepository;
    private final PostServiceImpl postService;


    public Article getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Article not found by this id :{} ", id);
                    return new ResourceNotFoundException("Article not found by this id :: " + id);
                });

        log.info("[getArticleById] << result: {}", article);

        return article;
    }

    public List<Article> createArticles(Long companyId, ArticlesDTO articlesDTO) {
        return articleRepository.saveAll(mapArticlesDtoToArticles(companyId, articlesDTO.getArticleDTOList()));
    }

    public Article createArticle(Long companyId, ArticleDTO articleDTO) {
        Post post = postService.getPostByNameAndCompanyId(companyId, articleDTO.getPostName());
        //TODO factory
        return articleRepository.save(Article.builder()
                .companyId(companyId)
                .imagePath(articleDTO.getImagePath())
                .post(post)
                .department(post.getDepartment())
                .theme(articleDTO.getTheme())
                .information(articleDTO.getInformation()).build());
    }

    private List<Article> mapArticlesDtoToArticles(Long companyId, List<ArticleDTO> articlesDTO) {

        List<Article> articleList = new ArrayList<>();

        for (ArticleDTO articleDTO : articlesDTO) {
            Post post = postService.getPostByNameAndCompanyId(companyId, articleDTO.getPostName());
            articleList.add(Article.builder()
                    .companyId(companyId)
                    .imagePath(articleDTO.getImagePath())
                    .post(post)
                    .department(post.getDepartment())
                    .theme(articleDTO.getTheme())
                    .information(articleDTO.getInformation()).build());
        }

        return articleList;
    }

    public void addArticleByIdToFavorite(Employee employee, Long articleId) {

        Article article = getArticleById(articleId);

        if (employee.getFavoriteArticles() == null || employee.getFavoriteArticles().isEmpty()) {
            List<Article> articles = new ArrayList<>(List.of(article));
            employee.setFavoriteArticles(articles);
        } else {
            employee.getFavoriteArticles().add(article);
        }

        employeeService.saveEmployee(employee);
    }

    public void deleteArticleByIdFromFavorite(Employee employee, Long articleId) {

        if (employee.getFavoriteArticles() == null || employee.getFavoriteArticles().isEmpty()) {
            return;
        }
        Article article = getArticleById(articleId);
        employee.getFavoriteArticles().remove(article);
        employeeService.saveEmployee(employee);
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public List<Article> getAllArticleByCompanyId(Long companyId) {
        return articleRepository.findAllByCompanyId(companyId);
    }

    public void deleteArticle(Article article) {
        articleRepository.delete(article);
    }
}
