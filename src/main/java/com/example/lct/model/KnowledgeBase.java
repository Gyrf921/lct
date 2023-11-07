package com.example.lct.model;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department department;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @Column(name = "company_id")
    private Long companyId;

    @OneToMany
    @JoinTable(
            name = "knowledge_base_articles",
            joinColumns = @JoinColumn(name = "knowledge_base_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private List<Article> articles;

    @OneToMany
    @JoinTable(
            name = "knowledge_base_videos",
            joinColumns = @JoinColumn(name = "knowledge_base_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id")
    )
    private List<Video> videos;

    @OneToMany
    @JoinTable(
            name = "knowledge_base_audios",
            joinColumns = @JoinColumn(name = "knowledge_base_id"),
            inverseJoinColumns = @JoinColumn(name = "audio_id")
    )
    private List<Audio> audio;

}
