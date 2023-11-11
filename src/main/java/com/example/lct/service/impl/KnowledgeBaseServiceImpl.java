package com.example.lct.service.impl;

import com.example.lct.mapper.KnowledgeMapper;
import com.example.lct.model.*;
import com.example.lct.service.KnowledgeBaseService;
import com.example.lct.web.dto.response.obj.ArticleResponseDTO;
import com.example.lct.web.dto.response.obj.MediaContentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private final PostServiceImpl postService;
    private final DepartmentServiceImpl departmentService;
    private final QuestionServiceImpl questionService;
    private final ArticleServiceImpl articleService;
    private final VideoServiceImpl videoService;
    private final AudioServiceImpl audioService;

    private final KnowledgeMapper knowledgeMapper;


    @Override
    public List<ArticleResponseDTO> getQuestions(Company companyByUserPrincipal) {
        return companyByUserPrincipal.getQuestions().stream()
                .map(knowledgeMapper::questionToArticleResponseDTO).toList();
    }

    @Override
    public List<ArticleResponseDTO> getArticles(Company companyByUserPrincipal) {
        return companyByUserPrincipal.getArticles().stream().map(knowledgeMapper::articleToArticleResponseDTO).toList();
    }

    @Override
    public List<ArticleResponseDTO> getFavouritesArticles(Employee employee) {
        return employee.getFavoriteArticles().stream()
                .map(knowledgeMapper::articleToArticleResponseDTO).toList();
    }

    @Override
    public List<MediaContentDTO> getVideos(Company companyByUserPrincipal) {
        return companyByUserPrincipal.getVideos().stream().map(knowledgeMapper::videoToMediaContentDTO).toList();
    }

    @Override
    public List<MediaContentDTO> getAudios(Company companyByUserPrincipal) {
        return companyByUserPrincipal.getAudio().stream().map(knowledgeMapper::audioToMediaContentDTO).toList();
    }

    @Override
    public Question getQuestionById(Long questionId) {
        return questionService.getQuestionById(questionId);
    }

    @Override
    public Article getArticleById(Long articleId) {
        return articleService.getArticleById(articleId);
    }

    @Override
    public Video getVideoById(Long videoId) {
        return videoService.getVideoById(videoId);
    }

    @Override
    public Audio getAudioById(Long audioId) {
        return audioService.getAudioById(audioId);
    }

    @Override
    public List<ArticleResponseDTO> getArticlesByDepartmentName(Company companyByUserPrincipal, String departmentName) {
        Department department = departmentService.getDepartmentByNameAndCompanyId(companyByUserPrincipal.getCompanyId(), departmentName);

        return companyByUserPrincipal.getArticles().stream()
                .filter(article -> article.getDepartment().equals(department))
                .map(knowledgeMapper::articleToArticleResponseDTO).toList();
    }

    @Override
    public List<ArticleResponseDTO> getArticlesByPostName(Company companyByUserPrincipal, String postName) {
        Post post = postService.getPostByNameAndCompanyId(companyByUserPrincipal.getCompanyId(), postName);

        return companyByUserPrincipal.getArticles().stream()
                .filter(article -> article.getPost().equals(post))
                .map(knowledgeMapper::articleToArticleResponseDTO).toList();
    }

    @Override
    public List<MediaContentDTO> getVideoByDepartmentName(Company companyByUserPrincipal, String departmentName) {
        Department department = departmentService.getDepartmentByNameAndCompanyId(companyByUserPrincipal.getCompanyId(), departmentName);

        return companyByUserPrincipal.getVideos().stream()
                .filter(article -> article.getDepartment().equals(department))
                .map(knowledgeMapper::videoToMediaContentDTO).toList();
    }

    @Override
    public List<MediaContentDTO> getVideoByPostName(Company companyByUserPrincipal, String postName) {
        Post post = postService.getPostByNameAndCompanyId(companyByUserPrincipal.getCompanyId(), postName);

        return companyByUserPrincipal.getVideos().stream()
                .filter(article -> article.getPost().equals(post))
                .map(knowledgeMapper::videoToMediaContentDTO).toList();
    }

    @Override
    public List<MediaContentDTO> getAudioByDepartmentName(Company companyByUserPrincipal, String departmentName) {
        Department department = departmentService.getDepartmentByNameAndCompanyId(companyByUserPrincipal.getCompanyId(), departmentName);

        return companyByUserPrincipal.getAudio().stream()
                .filter(article -> article.getDepartment().equals(department))
                .map(knowledgeMapper::audioToMediaContentDTO).toList();
    }

    @Override
    public List<MediaContentDTO> getAudioByPostName(Company companyByUserPrincipal, String postName) {
        Post post = postService.getPostByNameAndCompanyId(companyByUserPrincipal.getCompanyId(), postName);

        return companyByUserPrincipal.getAudio().stream()
                .filter(article -> article.getPost().equals(post))
                .map(knowledgeMapper::audioToMediaContentDTO).toList();
    }

    @Override
    public Boolean addArticleByIdToFavorite(Employee employee, Long articleId) {
        articleService.addArticleByIdToFavorite(employee, articleId);
        return true;
    }

    @Override
    public Boolean deleteArticleByIdFromFavorite(Employee employee, Long articleId) {
        articleService.deleteArticleByIdFromFavorite(employee, articleId);
        return true;
    }

}
