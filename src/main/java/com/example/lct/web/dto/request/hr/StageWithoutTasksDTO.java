package com.example.lct.web.dto.request.hr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StageWithoutTasksDTO {

    private String name;

    private Integer levelDifficulty;

    private LocalDateTime deadline;

}
