package com.example.lct.web.dto.request.admin;

import com.example.lct.web.dto.request.admin.obj.ArticleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticlesDTO {

    private List<ArticleDTO> articleDTOList;
}
