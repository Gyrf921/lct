package com.example.lct.web.controller.general;


import com.example.lct.mapper.KnowledgeMapper;
import com.example.lct.model.Article;
import com.example.lct.model.Audio;
import com.example.lct.model.Question;
import com.example.lct.model.Video;
import com.example.lct.model.enumformodel.HistoryType;
import com.example.lct.service.HistoryService;
import com.example.lct.service.KnowledgeBaseService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.response.ArticleListResponseDTO;
import com.example.lct.web.dto.response.obj.ArticleResponseDTO;
import com.example.lct.web.dto.response.obj.MediaContentDTO;
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
@RequestMapping("/knowledge-base")
public class KnowledgeBaseController {

    private final KnowledgeBaseService knowledgeBaseService;
    private final KnowledgeMapper knowledgeMapper;
    private final UserPrincipalUtils userPrincipalUtils;
    private final HistoryService historyService;

    //region without filter
    @Operation(summary = "get all company questions")
    @GetMapping("/questions")
    public ResponseEntity<List<ArticleResponseDTO>> getQuestions(Principal principal) {

        List<ArticleResponseDTO> articleListResponseDTO
                = knowledgeBaseService.getQuestions(userPrincipalUtils.getCompanyByUserPrincipal(principal));
        return ResponseEntity.ok().body(articleListResponseDTO);
    }

    @Operation(summary = "get all article")
    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponseDTO>> getArticles(Principal principal) {
        List<ArticleResponseDTO> articleListResponseDTO
                = knowledgeBaseService.getArticles(userPrincipalUtils.getCompanyByUserPrincipal(principal));
        return ResponseEntity.ok().body(articleListResponseDTO);
    }

    @Operation(summary = "get all favourites article")
    @GetMapping("/favorites/articles")
    public ResponseEntity<List<ArticleResponseDTO>> getFavouritesArticles(Principal principal) {
        List<ArticleResponseDTO> articleListResponseDTO
                = knowledgeBaseService.getFavouritesArticles(userPrincipalUtils.getEmployeeByUserPrincipal(principal));
        return ResponseEntity.ok().body(articleListResponseDTO);
    }

    @Operation(summary = "get all video")
    @GetMapping("/videos")
    public ResponseEntity<List<MediaContentDTO>> getVideos(Principal principal) {
        List<MediaContentDTO> mediaContentDTOS
                = knowledgeBaseService.getVideos(userPrincipalUtils.getCompanyByUserPrincipal(principal));
        return ResponseEntity.ok().body(mediaContentDTOS);
    }

    @Operation(summary = "get all audios")
    @GetMapping("/audios")
    public ResponseEntity<List<MediaContentDTO>> getAudios(Principal principal) {
        List<MediaContentDTO> mediaContentDTOS
                = knowledgeBaseService.getAudios(userPrincipalUtils.getCompanyByUserPrincipal(principal));
        return ResponseEntity.ok().body(mediaContentDTOS);
    }

    @Operation(summary = "get all company questions")
    @GetMapping("/questions/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable(value = "questionId") Long questionId,
                                                    Principal principal) {

        Question question = knowledgeBaseService.getQuestionById(questionId);

        historyService.createHistoryActionRead(userPrincipalUtils.getEmployeeByUserPrincipal(principal),
                HistoryType.ARTICLE, question.getTheme());

        return ResponseEntity.ok().body(question);
    }

    @Operation(summary = "get article to favourites")
    @GetMapping("/articles/{articleId}")
    public ResponseEntity<Article> getArticleById(@PathVariable(value = "articleId") Long articleId,
                                                  Principal principal) {
        Article article = knowledgeBaseService.getArticleById(articleId);

        historyService.createHistoryActionRead(userPrincipalUtils.getEmployeeByUserPrincipal(principal),
                HistoryType.ARTICLE, article.getTheme());
        return ResponseEntity.ok().body(article);
    }

    @Operation(summary = "get all video")
    @GetMapping("/videos/{videoId}")
    public ResponseEntity<MediaContentDTO> getVideoById(@PathVariable(value = "videoId") Long videoId,
                                                        Principal principal) {
        Video video = knowledgeBaseService.getVideoById(videoId);
        historyService.createHistoryActionRead(userPrincipalUtils.getEmployeeByUserPrincipal(principal),
                HistoryType.VIDEO, video.getName());
        return ResponseEntity.ok().body(knowledgeMapper.videoToMediaContentDTO(video));
    }

    @Operation(summary = "get all audio")
    @GetMapping("/audios/{audioId}")
    public ResponseEntity<MediaContentDTO> getAudioById(@PathVariable(value = "audioId") Long audioId,
                                                        Principal principal) {
        Audio audio = knowledgeBaseService.getAudioById(audioId);
        historyService.createHistoryActionRead(userPrincipalUtils.getEmployeeByUserPrincipal(principal),
                HistoryType.AUDIO, audio.getName());
        return ResponseEntity.ok().body(knowledgeMapper.audioToMediaContentDTO(audio));
    }

    //endregion

    //region filter
    @Operation(summary = "get all article by department")
    @GetMapping("/articles/departments")
    public ResponseEntity<List<ArticleResponseDTO>> getArticlesByDepartment(@RequestParam(value = "departmentName") String departmentName,
                                                                          Principal principal) {
        List<ArticleResponseDTO> articleListResponseDTO
                = knowledgeBaseService.getArticlesByDepartmentName(userPrincipalUtils.getCompanyByUserPrincipal(principal), departmentName);

        return ResponseEntity.ok().body(articleListResponseDTO);
    }

    @Operation(summary = "get all article by post")
    @GetMapping("/articles/posts")
    public ResponseEntity<List<ArticleResponseDTO>> getArticlesByPost(@RequestParam(value = "postName") String postName,
                                                                    Principal principal) {
        List<ArticleResponseDTO> articleListResponseDTO
                = knowledgeBaseService.getArticlesByPostName(userPrincipalUtils.getCompanyByUserPrincipal(principal), postName);
        return ResponseEntity.ok().body(articleListResponseDTO);
    }

    @Operation(summary = "get all video by Department")
    @GetMapping("/videos/departments")
    public ResponseEntity<List<MediaContentDTO>> getVideoByDepartment(@RequestParam(value = "departmentName") String departmentName,
                                                                      Principal principal) {
        List<MediaContentDTO> mediaContentDTOS
                = knowledgeBaseService.getVideoByDepartmentName(userPrincipalUtils.getCompanyByUserPrincipal(principal), departmentName);
        return ResponseEntity.ok().body(mediaContentDTOS);
    }

    @Operation(summary = "get all Video by post")
    @GetMapping("/videos/posts")
    public ResponseEntity<List<MediaContentDTO>> getVideoByPost(@RequestParam(value = "postName") String postName,
                                                                Principal principal) {
        List<MediaContentDTO> mediaContentDTOS
                = knowledgeBaseService.getVideoByPostName(userPrincipalUtils.getCompanyByUserPrincipal(principal), postName);
        return ResponseEntity.ok().body(mediaContentDTOS);
    }

    @Operation(summary = "get all Audio by department")
    @GetMapping("/audios/departments")
    public ResponseEntity<List<MediaContentDTO>> getAudioByDepartment(@RequestParam(value = "departmentName") String departmentName,
                                                                      Principal principal) {
        List<MediaContentDTO> mediaContentDTOS
                = knowledgeBaseService.getAudioByDepartmentName(userPrincipalUtils.getCompanyByUserPrincipal(principal), departmentName);
        return ResponseEntity.ok().body(mediaContentDTOS);
    }

    @Operation(summary = "get all Audio by post")
    @GetMapping("/audios/posts")
    public ResponseEntity<List<MediaContentDTO>> getAudioByPost(@RequestParam(value = "postName") String postName,
                                                                Principal principal) {
        List<MediaContentDTO> mediaContentDTOS
                = knowledgeBaseService.getAudioByPostName(userPrincipalUtils.getCompanyByUserPrincipal(principal), postName);

        return ResponseEntity.ok().body(mediaContentDTOS);
    }

    @Operation(summary = "add article to favourites")
    @PostMapping("/favorites/articles/{articleId}")
    public ResponseEntity<Boolean> addArticleByIdToFavorite(@PathVariable(value = "articleId") Long articleId,
                                                            Principal principal) {
        Boolean isSaved
                = knowledgeBaseService.addArticleByIdToFavorite(userPrincipalUtils.getEmployeeByUserPrincipal(principal), articleId);

        return ResponseEntity.ok().body(isSaved);
    }

    @Operation(summary = "delete article to favourites")
    @DeleteMapping("/favorites/articles/{articleId}")
    public ResponseEntity<Boolean> deleteArticleByIdFromFavorite(@PathVariable(value = "articleId") Long articleId,
                                                                 Principal principal) {
        Boolean isDeleted
                = knowledgeBaseService.deleteArticleByIdFromFavorite(userPrincipalUtils.getEmployeeByUserPrincipal(principal), articleId);

        return ResponseEntity.ok().body(isDeleted);
    }
    //endregion

}
