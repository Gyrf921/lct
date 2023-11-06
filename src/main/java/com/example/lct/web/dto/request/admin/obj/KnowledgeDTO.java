package com.example.lct.web.dto.request.admin.obj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeDTO {

    private String theme;

    private String information;
}
