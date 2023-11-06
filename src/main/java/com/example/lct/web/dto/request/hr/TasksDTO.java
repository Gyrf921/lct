package com.example.lct.web.dto.request.hr;

import com.example.lct.model.Task;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TasksDTO {

    List<TaskDTO> taskDTOList;
}
