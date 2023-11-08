package com.example.lct.repository;

import com.example.lct.model.Stage;
import com.example.lct.model.TaskStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskStageRepository extends JpaRepository<TaskStage, Long> {
    List<TaskStage> findAllByStage(Stage stage);
}
