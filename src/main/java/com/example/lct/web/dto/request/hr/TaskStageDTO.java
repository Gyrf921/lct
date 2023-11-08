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
public class TaskStageDTO {
    private Long taskId;
    private LocalDateTime deadline;
}
