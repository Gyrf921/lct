package com.example.lct.model.factory;

import com.example.lct.model.Post;
import com.example.lct.model.Task;
import com.example.lct.service.impl.PostServiceImpl;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskFactory {

    private TaskFactory() {
    }

    private static PostServiceImpl postService;

    @Autowired
    public void setDepartmentService(PostServiceImpl postService) {
        TaskFactory.postService = postService;
    }

    public static Task create(Long companyId, TaskDTO taskDTO) {
        return Task.builder().companyId(companyId)
                .post(getPostForTask(companyId, taskDTO.getPostName()))
                .name(taskDTO.getName())
                .description(taskDTO.getDescription())
                .levelDifficulty(taskDTO.getLevelDifficulty())
                .rate(taskDTO.getRate())
                .build();
    }

    public static Task createBase(Long companyId, TaskDTO taskDTO) {
        return Task.builder().companyId(companyId)
                .name(taskDTO.getName())
                .description(taskDTO.getDescription())
                .levelDifficulty(0)
                .rate(taskDTO.getRate())
                .build();
    }

    public static Task update(Task task, TaskDTO taskDTO) {
        return Task.builder()
                .taskId(task.getTaskId())
                .imagePath(taskDTO.getImagePath())
                .companyId(task.getCompanyId())
                .post(getPostForTask(task.getCompanyId(), taskDTO.getPostName()))
                .name(taskDTO.getName())
                .description(taskDTO.getDescription())
                .levelDifficulty(taskDTO.getLevelDifficulty())
                .rate(taskDTO.getRate())
                .build();
    }

    private static Post getPostForTask(Long companyId, String postName) {
        return postService.getPostByNameAndCompanyId(companyId, postName);
    }

}
