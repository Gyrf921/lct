package com.example.lct.model;

import com.example.lct.model.status.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "knowledge_bases")
public class KnowledgeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "knowledge_base_id")
    private Long knowledgeBaseId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @Column(name = "company_id")
    private Long companyId;

    @OneToMany
    @JoinTable(
            name = "knowledge_base_knowledges",
            joinColumns = @JoinColumn(name = "knowledge_base_id"),
            inverseJoinColumns = @JoinColumn(name = "knowledge_id")
    )
    private List<Knowledge> knowledge;

}
