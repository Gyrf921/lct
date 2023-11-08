package com.example.lct.web.dto.request.admin.obj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AudioDTO {

    private String postName;

    private String url;

    private String name;

    private String description;

}
