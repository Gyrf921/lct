package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.model.Article;
import com.example.lct.model.Employee;
import com.example.lct.model.KnowledgeBase;
import com.example.lct.repository.ArticleRepository;
import com.example.lct.service.EmployeeService;
import com.example.lct.web.dto.request.admin.KnowledgeBaseDTO;
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


    public Article getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Article not found by this id :{} ", id);
                    return new ResourceNotFoundException("Article not found by this id :: " + id);
                });

        log.info("[getArticleById] << result: {}", article);

        return article;
    }

    public KnowledgeBase createArticlesFromDtoToKnowledgeBase(KnowledgeBase knowledgeBase, KnowledgeBaseDTO knowledgeBaseDTO){
        List<Article> articles;
        if (isKnowledgeBaseHasArticles(knowledgeBase)){
            articles = knowledgeBase.getArticles();
            articles.addAll(mapArticlesDtoToArticles(knowledgeBase.getKnowledgeBaseId(), knowledgeBaseDTO.getArticlesDTO()));
        }
        else {
            articles = new ArrayList<>(mapArticlesDtoToArticles(knowledgeBase.getKnowledgeBaseId(), knowledgeBaseDTO.getArticlesDTO()));
        }
        List<Article> savedArticle = articleRepository.saveAll(articles);
        knowledgeBase.setArticles(savedArticle);
        return knowledgeBase;
    }

    private boolean isKnowledgeBaseHasArticles(KnowledgeBase knowledgeBase){
        return knowledgeBase.getArticles() != null && !knowledgeBase.getArticles().isEmpty();
    }
    private List<Article> mapArticlesDtoToArticles(Long knowledgeBaseId, List<ArticleDTO> ArticleDTOS){

        List<Article> articleList = new ArrayList<>();

        for (ArticleDTO knowledge : ArticleDTOS){
            articleList.add(Article.builder()
                    .knowledgeBaseId(knowledgeBaseId)
                    .theme(knowledge.getTheme())
                    .information(knowledge.getInformation()).build());
        }

        return articleList;
    }


    public void addArticleByIdToFavorite(Employee employee, Long articleId) {

        Article article = getArticleById(articleId);

        if (employee.getFavouriteArticles() == null || employee.getFavouriteArticles().isEmpty()){
            List<Article> articles = new ArrayList<>(List.of(article));
            employee.setFavouriteArticles(articles);
        }
        else {
            employee.getFavouriteArticles().add(article);
        }

        employeeService.saveEmployee(employee);
    }

    public void deleteArticleByIdFromFavorite(Employee employee, Long articleId) {

        if (employee.getFavouriteArticles() == null || employee.getFavouriteArticles().isEmpty()){
            return;
        }
        Article article = getArticleById(articleId);
        employee.getFavouriteArticles().remove(article);
        employeeService.saveEmployee(employee);
    }
}
