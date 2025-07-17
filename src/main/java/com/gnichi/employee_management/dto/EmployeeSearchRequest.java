package com.gnichi.employee_management.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeSearchRequest {
    private String firstname;
    private String lastname;
    private String jobTitle;
    private Long managerId;
}
