package com.example.lct.service;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.model.Task;
import com.example.lct.web.dto.request.admin.obj.EmployeeDTO;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;
import com.example.lct.web.dto.request.intern.TasksToCheckDTO;

import java.util.List;

public interface InternService {

    Boolean sendTasksToCheck(TasksToCheckDTO tasks);
}
