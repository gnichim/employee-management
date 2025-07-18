package com.gnichi.employee_management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gnichi.employee_management.entity.Employee;
import com.gnichi.employee_management.validation.CapitalizedWords;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeUpdateRequest {

    @NotBlank(message = "Firstname is required")
    private String firstname;

    @NotBlank(message = "Lastname is required")
    private String lastname;

    @CapitalizedWords
    private String jobTitle;

    @NotNull(message = "Contract start date is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate contractStartDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate contractEndDate;

    @NotNull(message = "Gender is required")
    private Employee.Gender gender;

    @NotNull(message = "Department is required")
    private Long departmentId;

    private Long managerId;
}

