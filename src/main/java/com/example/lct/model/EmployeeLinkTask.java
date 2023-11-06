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
@Table(name = "employee_task")
public class EmployeeLinkTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_task_id")
    private Long taskEmployeeId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    private Task task;

    @Column(name = "status", updatable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "deadline", insertable = false, updatable = false)
    private Timestamp deadline;

    @Column(name = "time_finish", insertable = false, updatable = false)
    private Timestamp timeFinish;
}
