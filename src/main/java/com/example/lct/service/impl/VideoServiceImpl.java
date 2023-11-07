package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.mapper.KnowledgeMapper;
import com.example.lct.model.Article;
import com.example.lct.model.KnowledgeBase;
import com.example.lct.model.Video;
import com.example.lct.repository.VideoRepository;
import com.example.lct.web.dto.request.admin.KnowledgeBaseDTO;
import com.example.lct.web.dto.request.admin.obj.ArticleDTO;
import com.example.lct.web.dto.request.admin.obj.VideoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoServiceImpl {

    private final VideoRepository videoRepository;
    private final KnowledgeMapper knowledgeMapper;

    public Video getVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Video not found by this id :{} ", id);
                    return new ResourceNotFoundException("Video not found by this id :: " + id);
                });

        log.info("[getVideoById] << result: {}", video);

        return video;
    }

    public KnowledgeBase createVideosFromDtoToKnowledgeBase(KnowledgeBase knowledgeBase, KnowledgeBaseDTO knowledgeBaseDTO) {

        List<Video> videos;
        if (isKnowledgeBaseHasVideos(knowledgeBase)){
            videos = knowledgeBase.getVideos();
            videos.addAll(mapAVideosDtoToVideos(knowledgeBaseDTO.getVideosDTO()));
        }
        else {
            videos = new ArrayList<>(mapAVideosDtoToVideos(knowledgeBaseDTO.getVideosDTO()));
        }

        knowledgeBase.setVideos(videos);

        return knowledgeBase;
    }
    private List<Video> mapAVideosDtoToVideos(List<VideoDTO> videoDTOS){

        List<Video> videos = new ArrayList<>();

        for (VideoDTO videoDTO : videoDTOS){
            videos.add(knowledgeMapper.videoDTOToVideo(videoDTO));
        }

        return videoRepository.saveAll(videos);
    }
    private boolean isKnowledgeBaseHasVideos(KnowledgeBase knowledgeBase){
        return knowledgeBase.getVideos() != null && !knowledgeBase.getVideos().isEmpty();
    }
}
