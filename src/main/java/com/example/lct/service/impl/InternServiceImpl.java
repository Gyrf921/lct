package com.example.lct.service.impl;

import com.example.lct.model.Task;
import com.example.lct.model.enumformodel.Status;
import com.example.lct.service.InternService;
import com.example.lct.service.TaskService;
import com.example.lct.web.dto.request.intern.TasksToCheckDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InternServiceImpl implements InternService {

    private final TaskService taskService;

    @Override
    public Boolean sendTasksToCheck(TasksToCheckDTO tasks) {

        for (Long taskEmployeeId: tasks.getTaskEmployeeIds()) {
            taskService.setStatusTaskForEmployee(taskEmployeeId, Status.REVIEW);
        }
        //TODO отправка имейлов
        return true;
    }
}
