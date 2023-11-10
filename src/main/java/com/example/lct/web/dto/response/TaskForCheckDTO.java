package com.example.lct.web.dto.response;

import com.example.lct.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskForCheckDTO {

    private Long employeeId;

    private String employeeName;

    private Post post;

    private Long taskStageId;

    private String taskName;

}
