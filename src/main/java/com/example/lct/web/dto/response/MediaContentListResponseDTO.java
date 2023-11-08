package com.example.lct.web.dto.response;

import com.example.lct.web.dto.response.obj.MediaContentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaContentListResponseDTO {

    List<MediaContentDTO> mediaContents;
}
