package com.example.lct.service;

import com.example.lct.model.*;
import com.example.lct.web.dto.response.obj.ArticleResponseDTO;
import com.example.lct.web.dto.response.obj.MediaContentDTO;

import java.util.List;

public interface KnowledgeBaseService {


    List<ArticleResponseDTO> getQuestions(Company companyByUserPrincipal);

    List<ArticleResponseDTO> getArticles(Company companyByUserPrincipal);

    List<ArticleResponseDTO> getFavouritesArticles(Employee employee);

    List<MediaContentDTO> getVideos(Company companyByUserPrincipal);

    List<MediaContentDTO> getAudios(Company companyByUserPrincipal);

    Question getQuestionById(Long questionId);

    Article getArticleById(Long articleId);

    Video getVideoById(Long videoId);

    Audio getAudioById(Long audioId);

    List<ArticleResponseDTO> getArticlesByDepartmentName(Company companyByUserPrincipal, String departmentName);

    List<ArticleResponseDTO> getArticlesByPostName(Company companyByUserPrincipal, String postName);

    List<MediaContentDTO> getVideoByDepartmentName(Company companyByUserPrincipal, String departmentName);

    List<MediaContentDTO> getVideoByPostName(Company companyByUserPrincipal, String postName);

    List<MediaContentDTO> getAudioByDepartmentName(Company companyByUserPrincipal, String departmentName);

    List<MediaContentDTO> getAudioByPostName(Company companyByUserPrincipal, String postName);

    Boolean addArticleByIdToFavorite(Employee employee, Long articleId);

    Boolean deleteArticleByIdFromFavorite(Employee employee, Long articleId);
}
