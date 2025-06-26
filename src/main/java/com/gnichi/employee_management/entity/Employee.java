package com.gnichi.employee_management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee_tbl")
public class Employee {

    @Id
    @GeneratedValue
    private long id;
    @Column(length = 50)
    private String firstname;
    @Column(length = 50)
    private String lastname;
    private String jobTitle;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate contractStartDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate contractEndDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    // Creates the foreign key department_id in employee_tbl
    @JoinColumn(name = "department_id")
    private Department department;

    public enum Gender {
        MALE,
        FEMALE
    }
}
