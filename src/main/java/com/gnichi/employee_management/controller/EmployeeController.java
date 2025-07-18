package com.gnichi.employee_management.controller;

import com.gnichi.employee_management.dto.EmployeeCreateRequest;
import com.gnichi.employee_management.dto.EmployeeSearchRequest;
import com.gnichi.employee_management.dto.EmployeeUpdateRequest;
import com.gnichi.employee_management.entity.Employee;
import com.gnichi.employee_management.exception.ApiErrorResponse;
import com.gnichi.employee_management.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());

            ApiErrorResponse response = new ApiErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    "Validation failed",
                    "/api/employees"
            );
            response.setMessage(String.join("; ", errors));

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Employee saved = employeeService.createEmployee(request);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    /* @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        return employeeService.getEmployeeById(id)
                .map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    } */

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        Employee employee = employeeService.getEmployeeById(id);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    /* @GetMapping("/get-by-firstname/{name}")
    public ResponseEntity<Employee> getEmployeeByFirstName(@PathVariable String name) {
        return employeeService.getEmployeeByName(name)
                .map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    } */

    @GetMapping("/get-by-firstname/{name}")
    public ResponseEntity<Employee> getEmployeeByFirstName(@PathVariable String name) {
        Employee employee = employeeService.getEmployeeByName(name);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("/get-by-department-id/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        return new ResponseEntity<>(employeeService.fetchEmployeesByDepartmentId(departmentId), HttpStatus.OK);
    }

    /* @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
        employee.setId(id); // ensure the path ID is synced with the object
        return employeeService.updateEmployee(employee)
                .map(updatedEmployee -> new ResponseEntity<>(updatedEmployee, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    } */

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());

            ApiErrorResponse response = new ApiErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    "Validation failed",
                    "/api/employees/" + id
            );
            response.setMessage(String.join("; ", errors));

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Employee updated = employeeService.updateEmployee(id, request);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }



    /* @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id) {
        return employeeService.removeEmployee(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        employeeService.removeEmployee(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get-by-manager-id/{managerId}")
    public ResponseEntity<List<Employee>> getEmployeesByManagerId(@PathVariable Long managerId) {
        List<Employee> employees = employeeService.getEmployeesByManagerId(managerId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestBody EmployeeSearchRequest request) {
        List<Employee> result = employeeService.searchEmployees(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}

