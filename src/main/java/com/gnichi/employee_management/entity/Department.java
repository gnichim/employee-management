package com.gnichi.employee_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department_tbl")
public class Department {

    @Id
    @GeneratedValue
    private long id;
    @Column(length = 50)
    private String name;
}
