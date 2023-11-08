package com.example.lct.repository;

import com.example.lct.model.Post;
import com.example.lct.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByCompanyIdAndPostAndLevelDifficulty(Long companyId, Post post, Integer levelDifficulty);

    List<Task> findAllByCompanyId(Long companyId);

}