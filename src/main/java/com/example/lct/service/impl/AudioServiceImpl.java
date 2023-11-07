package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.mapper.KnowledgeMapper;
import com.example.lct.model.Article;
import com.example.lct.model.Audio;
import com.example.lct.model.KnowledgeBase;
import com.example.lct.model.Video;
import com.example.lct.repository.AudioRepository;
import com.example.lct.repository.VideoRepository;
import com.example.lct.web.dto.request.admin.KnowledgeBaseDTO;
import com.example.lct.web.dto.request.admin.obj.AudioDTO;
import com.example.lct.web.dto.request.admin.obj.VideoDTO;
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
    public Audio getAudioById(Long id) {
        Audio audio = audioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Audio not found by this id :{} ", id);
                    return new ResourceNotFoundException("Audio not found by this id :: " + id);
                });

        log.info("[getAudioById] << result: {}", audio);

        return audio;
    }

    public KnowledgeBase createAudiosFromDtoToKnowledgeBase(KnowledgeBase knowledgeBase, KnowledgeBaseDTO knowledgeBaseDTO) {

        List<Audio> audios;
        if (isKnowledgeBaseHasAudios(knowledgeBase)){
            audios = knowledgeBase.getAudio();
            audios.addAll(mapAudiosDtoToAudios(knowledgeBaseDTO.getAudiosDTO()));
        }
        else {
            audios = new ArrayList<>(mapAudiosDtoToAudios(knowledgeBaseDTO.getAudiosDTO()));
        }

        knowledgeBase.setAudio(audios);

        return knowledgeBase;
    }

    private List<Audio> mapAudiosDtoToAudios(List<AudioDTO> audioDTOS){

        List<Audio> audios = new ArrayList<>();

        for (AudioDTO audioDTO : audioDTOS){
            audios.add(knowledgeMapper.audioDTOToAudio(audioDTO));
        }

        return audioRepository.saveAll(audios);
    }
    private boolean isKnowledgeBaseHasAudios(KnowledgeBase knowledgeBase){
        return knowledgeBase.getAudio() != null && !knowledgeBase.getAudio().isEmpty();
    }
}
