package com.example.lct.web.dto.request.admin;

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
public class VideosDTO {

    private List<VideoDTO> videoDTOList;

}
