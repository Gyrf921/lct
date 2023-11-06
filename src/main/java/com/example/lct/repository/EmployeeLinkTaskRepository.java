package com.example.lct.repository;

import com.example.lct.model.Employee;
import com.example.lct.model.EmployeeLinkTask;
import com.example.lct.model.Post;
import com.example.lct.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeLinkTaskRepository extends JpaRepository<EmployeeLinkTask, Long> {

    List<EmployeeLinkTask> findAllByEmployee(Employee employee);
    List<EmployeeLinkTask> findAllByEmployeeAndStatus(Employee employee, String status);


}