package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.mapper.KnowledgeMapper;
import com.example.lct.model.Audio;
import com.example.lct.model.Post;
import com.example.lct.repository.AudioRepository;
import com.example.lct.web.dto.request.admin.AudiosDTO;
import com.example.lct.web.dto.request.admin.obj.AudioDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AudioServiceImpl {
    private final AudioRepository audioRepository;

    private final KnowledgeMapper knowledgeMapper;

    private final PostServiceImpl postService;

    public Audio getAudioById(Long id) {
        Audio audio = audioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Audio not found by this id :{} ", id);
                    return new ResourceNotFoundException("Audio not found by this id :: " + id);
                });

        log.info("[getAudioById] << result: {}", audio);

        return audio;
    }

    public List<Audio> createAudios(Long companyId, AudiosDTO audiosDTO) {
        return audioRepository.saveAll(mapAudiosDtoToAudios(companyId, audiosDTO.getAudioDTOList()));
    }

    public Audio createAudio(Long companyId, AudioDTO audioDTO) {
        Post post = postService.getPostByNameAndCompanyId(companyId, audioDTO.getPostName());
        Audio audio = knowledgeMapper.audioDTOToAudio(audioDTO);
        audio.setCompanyId(companyId);
        audio.setPost(post);
        audio.setDepartment(post.getDepartment());

        return audioRepository.save(audio);
    }

    private List<Audio> mapAudiosDtoToAudios(Long companyId, List<AudioDTO> audioDTOS) {

        List<Audio> audios = new ArrayList<>();

        for (AudioDTO audioDTO : audioDTOS) {
            Post post = postService.getPostByNameAndCompanyId(companyId, audioDTO.getPostName());
            Audio audio = knowledgeMapper.audioDTOToAudio(audioDTO);
            audio.setCompanyId(companyId);
            audio.setPost(post);
            audio.setDepartment(post.getDepartment());
            audios.add(audio);
        }
        return audios;
    }

}
