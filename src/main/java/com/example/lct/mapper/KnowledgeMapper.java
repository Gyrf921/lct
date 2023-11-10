package com.example.lct.mapper;

import com.example.lct.model.Article;
import com.example.lct.model.Audio;
import com.example.lct.model.Question;
import com.example.lct.model.Video;
import com.example.lct.web.dto.request.admin.obj.AudioDTO;
import com.example.lct.web.dto.request.admin.obj.VideoDTO;
import com.example.lct.web.dto.response.obj.ArticleResponseDTO;
import com.example.lct.web.dto.response.obj.MediaContentDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface KnowledgeMapper {
    @Mapping(source = "questionId", target = "id")
    @Mapping(source = "theme", target = "name")
    ArticleResponseDTO questionToArticleResponseDTO(Question question);

    @Mapping(source = "articleId", target = "id")
    @Mapping(source = "theme", target = "name")
    ArticleResponseDTO articleToArticleResponseDTO(Article article);

    @Mapping(source = "videoId", target = "id")
    @Mapping(source = "post.name", target = "postName")
    MediaContentDTO videoToMediaContentDTO(Video video);

    @Mapping(source = "audioId", target = "id")
    @Mapping(source = "post.name", target = "postName")
    MediaContentDTO audioToMediaContentDTO(Audio audio);


    Video videoDTOToVideo(VideoDTO videoDTO);

    Audio audioDTOToAudio(AudioDTO audioDTO);
}
