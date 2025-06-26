package com.gnichi.employee_management.controller;

import com.gnichi.employee_management.entity.Department;
import com.gnichi.employee_management.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department saved = departmentService.createDepartment(department);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    /* @GetMapping("get-by-id/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable long id) {
        return departmentService.getDepartmentById(id)
                .map(department -> new ResponseEntity<>(department, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    } */

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable long id) {
        Department dept = departmentService.getDepartmentById(id); // throws if not found

        return new ResponseEntity<>(dept, HttpStatus.OK);
    }

    /* @GetMapping("get-by-name/{name}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartmentByName(name)
                .map(department -> new ResponseEntity<>(department, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    } */

    @GetMapping("get-by-name/{name}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String name) {
        Department dept = departmentService.getDepartmentByName(name);

        return new ResponseEntity<>(dept, HttpStatus.OK);
    }

    /* @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable long id, @RequestBody Department department) {
        department.setId(id);
        return departmentService.updateDepartment(department)
                .map(updatedDepartment -> new ResponseEntity<>(updatedDepartment, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    } */

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable long id, @RequestBody Department department) {
        department.setId(id); // ensure the path ID is synced with the object

        Department result = departmentService.updateDepartment(department);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /* @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable long id) {
        return departmentService.removeDepartment(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable long id) {
        departmentService.removeDepartment(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
