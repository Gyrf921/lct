package com.example.lct.model;

import com.example.lct.model.enumformodel.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task_stage")
public class TaskStage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_stage_id")
    private Long taskStageId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stage_id", referencedColumnName = "stage_id")
    private Stage stage;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    private Task task;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "answer_url")
    private String answerUrl;

    @Column(name = "deadline", insertable = false)
    private Timestamp deadline;

    @Column(name = "time_finish", insertable = false)
    private Timestamp timeFinish;
}
