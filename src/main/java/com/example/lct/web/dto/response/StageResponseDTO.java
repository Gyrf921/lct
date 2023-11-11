package com.example.lct.web.dto.response;

import com.example.lct.model.Stage;
import com.example.lct.model.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StageResponseDTO {

    private Stage stage;

    private List<Task> tasks;
}
