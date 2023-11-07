package com.example.lct.service;

import com.example.lct.web.dto.request.intern.TasksToCheckDTO;

public interface InternService {

    Boolean sendTasksToCheck(TasksToCheckDTO tasks);
}
