package com.example.lct.model;

import com.example.lct.model.enumformodel.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "stages")
public class Stage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stage_id")
    private Long stageId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "name")
    private String name;

    @Column(name = "level_difficulty")
    private Integer levelDifficulty;

    @Column(name = "test_url")
    private String testUrl;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "deadline", insertable = false)
    private Timestamp deadline;

    @Column(name = "time_finish", insertable = false)
    private Timestamp timeFinish;
}
