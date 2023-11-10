package com.example.lct.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;

@MappedSuperclass
@Data
public class BaseEntity {

    @CreatedDate
    @Column(name = "created")
    private Date created;
}
