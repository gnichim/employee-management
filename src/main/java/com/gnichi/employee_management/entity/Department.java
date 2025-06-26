package com.gnichi.employee_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    // mappedBy = "department" points to the property inside Employee.
    // cascade = CascadeType.ALL means changes to a department will propagate to its employees.
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    // @JsonIgnore will prevent employees from being serialized when returning a
    // department, avoiding the loop.
    @JsonIgnore
    private List<Employee> employees = new ArrayList<>();
}
