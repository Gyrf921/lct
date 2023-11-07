package com.example.lct.web.dto.request.admin;

import com.example.lct.web.dto.request.admin.obj.ArticleDTO;
import com.example.lct.web.dto.request.admin.obj.AudioDTO;
import com.example.lct.web.dto.request.admin.obj.VideoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeBaseDTO {

    private String postName;

    private List<ArticleDTO> articlesDTO;

    private List<VideoDTO> videosDTO;

    private List<AudioDTO> audiosDTO;

}
