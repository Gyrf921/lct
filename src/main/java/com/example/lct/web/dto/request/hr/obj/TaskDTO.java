package com.example.lct.web.dto.request.hr.obj;

import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {

    private String postName;

    private String name;

    private String description;

    @Max(value = 6)
    private Integer levelDifficulty;

    private Long rate;
}
