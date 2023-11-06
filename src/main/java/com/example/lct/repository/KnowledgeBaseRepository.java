package com.example.lct.repository;

import com.example.lct.model.Knowledge;
import com.example.lct.model.KnowledgeBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeBaseRepository extends JpaRepository<KnowledgeBase, Long> {
}
