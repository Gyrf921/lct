package com.example.lct.web.dto.request.intern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TasksToCheckDTO {

    List<Long> taskEmployeeIds;
}
