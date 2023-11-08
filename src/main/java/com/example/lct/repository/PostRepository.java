package com.example.lct.repository;

import com.example.lct.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByNameAndCompanyId(String name, Long companyId);
}
