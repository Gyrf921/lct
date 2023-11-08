package com.example.lct.web.dto.request.admin;

import com.example.lct.web.dto.request.admin.obj.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsDTO {

    private List<PostDTO> postDTOList;
}
