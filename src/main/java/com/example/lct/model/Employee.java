package com.example.lct.model;

import com.example.lct.model.status.Achievement;
import com.example.lct.model.status.Department;
import com.example.lct.model.status.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinTable(
            name = "employee_post",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Post post;

    @Column(name = "level_difficulty")
    private Integer levelDifficulty;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "social_network")
    private String socialNetwork;

    @Column(name = "city")
    private String city;

    @ManyToMany
    @JoinTable(
            name = "employee_role",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @ManyToMany
    @JoinTable(
            name = "employee_task",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<Task> tasks;

    @ManyToMany
    @JoinTable(
            name = "employee_knowledge_favourites",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "knowledge_id")
    )
    private List<Knowledge> knowledgeList;

    @OneToMany
    @JoinTable(
            name = "employee_achievement",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_id")
    )
    private List<Achievement> achievements;

    @Column(name = "curator_id")
    private Long curatorId; //Hr id

    @Column(name = "account")
    private Long account;

}
