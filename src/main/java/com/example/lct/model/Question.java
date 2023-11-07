package com.example.lct.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "theme")
    private String theme;

    @Column(name = "answer")
    private String answer;

}
