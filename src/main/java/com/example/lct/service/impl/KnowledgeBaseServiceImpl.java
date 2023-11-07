package com.example.lct.service.impl;

import com.example.lct.mapper.KnowledgeMapper;
import com.example.lct.model.*;
import com.example.lct.repository.KnowledgeBaseRepository;
import com.example.lct.service.KnowledgeBaseService;
import com.example.lct.web.dto.request.admin.KnowledgeBaseDTO;
import com.example.lct.web.dto.response.obj.ArticleResponseDTO;
import com.example.lct.web.dto.response.obj.MediaContentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private final KnowledgeBaseRepository knowledgeBaseRepository;

    private final PostServiceImpl postService;
    private final DepartmentServiceImpl departmentService;
    private final QuestionServiceImpl questionService;
    private final ArticleServiceImpl articleService;
    private final VideoServiceImpl videoService;
    private final AudioServiceImpl audioService;

    private final KnowledgeMapper knowledgeMapper;


    @Override
    public KnowledgeBase createKnowledgeBaseForCompany(Company company, KnowledgeBaseDTO knowledgeBaseDTO) {
        //Создать KnowledgeBase, создать Article с ссылкой на KnowledgeBase, добавить в KnowledgeBase

        Post post = postService.getPostByNameAndCompanyId(company.getCompanyId(), knowledgeBaseDTO.getPostName());

        KnowledgeBase knowledgeBase = knowledgeBaseRepository.save(KnowledgeBase.builder()
                .companyId(company.getCompanyId())
                .post(post).build());

        knowledgeBase = articleService.createArticlesFromDtoToKnowledgeBase(knowledgeBase, knowledgeBaseDTO);
        knowledgeBase = videoService.createVideosFromDtoToKnowledgeBase(knowledgeBase, knowledgeBaseDTO);
        knowledgeBase = audioService.createAudiosFromDtoToKnowledgeBase(knowledgeBase, knowledgeBaseDTO);

        KnowledgeBase knowledgeBaseSaved = knowledgeBaseRepository.save(knowledgeBase);

        log.info("[saveKnowledgeBaseForCompany] << result: {}", knowledgeBaseSaved);
        return knowledgeBaseSaved;
    }

    @Override
    public List<ArticleResponseDTO> getQuestions(Company companyByUserPrincipal) {
        return companyByUserPrincipal.getQuestions().stream()
                .map(knowledgeMapper::questionToArticleResponseDTO).toList();
    }

    @Override
    public List<ArticleResponseDTO> getArticles(Company companyByUserPrincipal) {
        List<ArticleResponseDTO> articleResponseDTOS = new ArrayList<>();
        for (KnowledgeBase knowledgeBase: companyByUserPrincipal.getKnowledgeBases()) {
            articleResponseDTOS.addAll(mapListArticleToResponseDTO(knowledgeBase));
        }
        return articleResponseDTOS;
    }

    @Override
    public List<ArticleResponseDTO> getFavouritesArticles(Employee employee) {
        return employee.getFavouriteArticles().stream()
                .map(knowledgeMapper::articleToArticleResponseDTO).toList();
    }

    @Override
    public List<MediaContentDTO> getVideos(Company companyByUserPrincipal) {
        List<MediaContentDTO> mediaContentDTOS = new ArrayList<>();
        for (KnowledgeBase knowledgeBase: companyByUserPrincipal.getKnowledgeBases()) {
            mediaContentDTOS.addAll(knowledgeBase.getVideos().stream().map(knowledgeMapper::videoToMediaContentDTO).toList());
        }
        return mediaContentDTOS;
    }

    @Override
    public List<MediaContentDTO> getAudios(Company companyByUserPrincipal) {
        List<MediaContentDTO> mediaContentDTOS = new ArrayList<>();
        for (KnowledgeBase knowledgeBase: companyByUserPrincipal.getKnowledgeBases()) {
            mediaContentDTOS.addAll(knowledgeBase.getAudio().stream().map(knowledgeMapper::audioToMediaContentDTO).toList());
        }
        return mediaContentDTOS;
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

        List<ArticleResponseDTO> articleResponseDTOS = new ArrayList<>();
        for (KnowledgeBase knowledgeBase: companyByUserPrincipal.getKnowledgeBases()) {
            if(knowledgeBase.getDepartment().equals(department)){
                articleResponseDTOS.addAll(mapListArticleToResponseDTO(knowledgeBase));
            }
        }

        return articleResponseDTOS;
    }

    @Override
    public List<ArticleResponseDTO> getArticlesByPostName(Company companyByUserPrincipal, String postName) {
        Post post = postService.getPostByNameAndCompanyId(companyByUserPrincipal.getCompanyId(), postName);

        List<ArticleResponseDTO> articleResponseDTOS = new ArrayList<>();
        for (KnowledgeBase knowledgeBase: companyByUserPrincipal.getKnowledgeBases()) {
            if(knowledgeBase.getPost().equals(post)){
                articleResponseDTOS.addAll(mapListArticleToResponseDTO(knowledgeBase));
            }
        }

        return articleResponseDTOS;
    }

    @Override
    public List<MediaContentDTO> getVideoByDepartmentName(Company companyByUserPrincipal, String departmentName) {
        Department department = departmentService.getDepartmentByNameAndCompanyId(companyByUserPrincipal.getCompanyId(), departmentName);

        List<MediaContentDTO> mediaContentDTOS = new ArrayList<>();
        for (KnowledgeBase knowledgeBase: companyByUserPrincipal.getKnowledgeBases()) {
            if(knowledgeBase.getDepartment().equals(department)){
                mediaContentDTOS.addAll(mapListMediaToResponseDTO(knowledgeBase, true));
            }
        }

        return mediaContentDTOS;
    }

    @Override
    public List<MediaContentDTO> getVideoByPostName(Company companyByUserPrincipal, String postName) {
        Post post = postService.getPostByNameAndCompanyId(companyByUserPrincipal.getCompanyId(), postName);

        List<MediaContentDTO> mediaContentDTOS = new ArrayList<>();
        for (KnowledgeBase knowledgeBase: companyByUserPrincipal.getKnowledgeBases()) {
            if(knowledgeBase.getPost().equals(post)){
                mediaContentDTOS.addAll(mapListMediaToResponseDTO(knowledgeBase, true));
            }
        }

        return mediaContentDTOS;
    }

    @Override
    public List<MediaContentDTO> getAudioByDepartmentName(Company companyByUserPrincipal, String departmentName) {
        Department department = departmentService.getDepartmentByNameAndCompanyId(companyByUserPrincipal.getCompanyId(), departmentName);

        List<MediaContentDTO> mediaContentDTOS = new ArrayList<>();
        for (KnowledgeBase knowledgeBase: companyByUserPrincipal.getKnowledgeBases()) {
            if(knowledgeBase.getDepartment().equals(department)){
                mediaContentDTOS.addAll(mapListMediaToResponseDTO(knowledgeBase, false));
            }
        }

        return mediaContentDTOS;
    }

    @Override
    public List<MediaContentDTO> getAudioByPostName(Company companyByUserPrincipal, String postName) {
        Post post = postService.getPostByNameAndCompanyId(companyByUserPrincipal.getCompanyId(), postName);

        List<MediaContentDTO> mediaContentDTOS = new ArrayList<>();
        for (KnowledgeBase knowledgeBase: companyByUserPrincipal.getKnowledgeBases()) {
            if(knowledgeBase.getPost().equals(post)){
                mediaContentDTOS.addAll(mapListMediaToResponseDTO(knowledgeBase, false));
            }
        }

        return mediaContentDTOS;
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

    private List<ArticleResponseDTO> mapListArticleToResponseDTO(KnowledgeBase knowledgeBase){
        return  knowledgeBase.getArticles().stream().map(knowledgeMapper::articleToArticleResponseDTO).toList();
    }

    private List<MediaContentDTO> mapListMediaToResponseDTO(KnowledgeBase knowledgeBase, boolean isVideo){
        if (isVideo){
            return  knowledgeBase.getVideos().stream().map(knowledgeMapper::videoToMediaContentDTO).toList();
        }
        return  knowledgeBase.getAudio().stream().map(knowledgeMapper::audioToMediaContentDTO).toList();
    }
}
