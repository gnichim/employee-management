package com.gnichi.employee_management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee_tbl")
public class Employee {

    @Id
    @GeneratedValue
    /*
    Default value of long is 0 (a valid value), and the default value of Long is null (means not persisted)
    * When we use @GeneratedValue, Hibernate expects the ID to be null before persisting—so it knows
    * it needs to generate one. But if we use long, the default value is 0, which can confuse Hibernate
    * into thinking the entity is already persisted or cause issues with identity generation.
    * */
    private Long id;
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

    // Add a self-referencing @ManyToOne relationship to represent the manager (since the manager is also an employee)
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    @JsonIgnore // Prevent infinite recursion
    private List<Employee> subordinates = new ArrayList<>();

    public enum Gender {
        MALE,
        FEMALE
    }
}
