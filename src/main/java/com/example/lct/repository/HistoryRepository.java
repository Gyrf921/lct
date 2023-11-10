package com.example.lct.repository;

import com.example.lct.model.Employee;
import com.example.lct.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findAllByEmployee(Employee employee);
}
