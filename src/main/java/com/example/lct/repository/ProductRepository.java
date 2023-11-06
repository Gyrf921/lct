package com.example.lct.repository;

import com.example.lct.model.status.Department;
import com.example.lct.model.status.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
