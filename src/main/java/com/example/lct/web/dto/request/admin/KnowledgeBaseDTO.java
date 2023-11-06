package com.example.lct.web.dto.request.admin;

import com.example.lct.model.status.Department;
import com.example.lct.web.dto.request.admin.obj.KnowledgeDTO;
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

    private List<KnowledgeDTO> knowledge;
}
