package com.gnichi.employee_management.controller;

import com.gnichi.employee_management.entity.Employee;
import com.gnichi.employee_management.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee saved = employeeService.createEmployee(employee);

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
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
        employee.setId(id); // ensure the path ID is synced with the object

        Employee result = employeeService.updateEmployee(employee);

        return new ResponseEntity<>(result, HttpStatus.OK);
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

}

