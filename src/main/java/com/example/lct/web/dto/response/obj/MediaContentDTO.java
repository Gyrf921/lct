package com.example.lct.web.dto.response.obj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaContentDTO {

    private Long id;

    private String url;

    private String name;

    private String description;

    private String postName;
}
