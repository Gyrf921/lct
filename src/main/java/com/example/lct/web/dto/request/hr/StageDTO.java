package com.example.lct.web.dto.request.hr;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StageDTO {

    private String name;

    private Integer levelDifficulty;

    private LocalDateTime deadline;

    private TasksDTO tasksDTO;

    private Long internId;
}
