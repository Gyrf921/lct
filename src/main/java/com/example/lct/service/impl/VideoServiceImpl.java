package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.mapper.KnowledgeMapper;
import com.example.lct.model.Post;
import com.example.lct.model.Video;
import com.example.lct.repository.VideoRepository;
import com.example.lct.web.dto.request.admin.VideosDTO;
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

    private final PostServiceImpl postService;

    public Video getVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Video not found by this id :{} ", id);
                    return new ResourceNotFoundException("Video not found by this id :: " + id);
                });

        log.info("[getVideoById] << result: {}", video);

        return video;
    }

    public List<Video> createVideos(Long companyId, VideosDTO videosDTO) {
        return videoRepository.saveAll(mapVideosDtoToVideos(companyId, videosDTO.getVideoDTOList()));
    }

    public Video createVideo(Long companyId, VideoDTO videoDTO) {
        Post post = postService.getPostByNameAndCompanyId(companyId, videoDTO.getPostName());
        Video video = knowledgeMapper.videoDTOToVideo(videoDTO);
        video.setCompanyId(companyId);
        video.setPost(post);
        video.setDepartment(post.getDepartment());
        return videoRepository.save(video);
    }


    private List<Video> mapVideosDtoToVideos(Long companyId, List<VideoDTO> videoDTOS) {
        List<Video> videos = new ArrayList<>();

        for (VideoDTO videoDTO : videoDTOS) {
            Post post = postService.getPostByNameAndCompanyId(companyId, videoDTO.getPostName());
            Video video = knowledgeMapper.videoDTOToVideo(videoDTO);
            video.setCompanyId(companyId);
            video.setPost(post);
            video.setDepartment(post.getDepartment());
            videos.add(video);
        }

        return videos;
    }


}
