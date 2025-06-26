package com.gnichi.employee_management.controller;

import com.gnichi.employee_management.entity.Employee;
import com.gnichi.employee_management.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
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
    public ResponseEntity<?> getEmployeeById(@PathVariable long id) {
        Optional<Employee> optional = employeeService.getEmployeeById(id);

        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("timestamp", LocalDateTime.now());
            errorDetails.put("status", HttpStatus.NOT_FOUND.value());
            errorDetails.put("error", "Employee not found");
            errorDetails.put("message", "No employee found with id: " + id);
            errorDetails.put("path", "/api/employees/get-by-id/" + id);

            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    /* @GetMapping("/get-by-firstname/{name}")
    public ResponseEntity<Employee> getEmployeeByFirstName(@PathVariable String name) {
        return employeeService.getEmployeeByName(name)
                .map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    } */

    @GetMapping("/get-by-firstname/{name}")
    public ResponseEntity<?> getEmployeeByFirstName(@PathVariable String name) {
        Optional<Employee> optional = employeeService.getEmployeeByName(name);

        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("timestamp", LocalDateTime.now());
            errorDetails.put("status", HttpStatus.NOT_FOUND.value());
            errorDetails.put("error", "Employee not found");
            errorDetails.put("message", "No employee found with firstname: " + name);
            errorDetails.put("path", "/api/employees/get-by-firstname/" + name);

            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
        employee.setId(id); // ensure the path ID is synced with the object

        Optional<Employee> result = employeeService.updateEmployee(employee);

        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        } else {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("timestamp", LocalDateTime.now());
            errorDetails.put("status", HttpStatus.NOT_FOUND.value());
            errorDetails.put("error", "Employee not found");
            errorDetails.put("message", "No employee found with ID: " + id);
            errorDetails.put("path", "/api/employees/" + id);

            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }


    /* @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id) {
        return employeeService.removeEmployee(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable long id) {
        boolean deleted = employeeService.removeEmployee(id);

        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("error", "Employee not found");
            response.put("message", "No employee found with ID: " + id);
            response.put("path", "/api/employees/" + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}

