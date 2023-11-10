package com.example.lct.repository;

import com.example.lct.model.Employee;
import com.example.lct.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    List<Employee> findAllByCuratorId(Long curatorId);

    List<Employee> findAllByRolesContaining(Role role);

}

