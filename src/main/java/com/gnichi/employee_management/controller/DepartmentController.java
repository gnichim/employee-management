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
        return new ResponseEntity<>(departmentService.createDepartment(department),
                HttpStatus.CREATED);
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
    public ResponseEntity<?> getDepartmentById(@PathVariable long id) {
        Optional<Department> optional = departmentService.getDepartmentById(id);

        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("timestamp", LocalDateTime.now());
            errorDetails.put("status", HttpStatus.NOT_FOUND.value());
            errorDetails.put("error", "Department not found");
            errorDetails.put("message", "No department found with id: " + id);
            errorDetails.put("path", "/api/departments/get-by-id/" + id);

            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    /* @GetMapping("get-by-name/{name}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartmentByName(name)
                .map(department -> new ResponseEntity<>(department, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    } */

    @GetMapping("get-by-name/{name}")
    public ResponseEntity<?> getDepartmentByName(@PathVariable String name) {
        Optional<Department> optional = departmentService.getDepartmentByName(name);

        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("timestamp", LocalDateTime.now());
            errorDetails.put("status", HttpStatus.NOT_FOUND.value());
            errorDetails.put("error", "Department not found");
            errorDetails.put("message", "No department found with name: " + name);
            errorDetails.put("path", "/api/departments/get-by-name/" + name);

            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    /* @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable long id, @RequestBody Department department) {
        department.setId(id);
        return departmentService.updateDepartment(department)
                .map(updatedDepartment -> new ResponseEntity<>(updatedDepartment, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    } */

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable long id, @RequestBody Department department) {
        department.setId(id); // ensure the path ID is synced with the object

        Optional<Department> result = departmentService.updateDepartment(department);

        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        } else {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("timestamp", LocalDateTime.now());
            errorDetails.put("status", HttpStatus.NOT_FOUND.value());
            errorDetails.put("error", "Department not found");
            errorDetails.put("message", "No department found with ID: " + id);
            errorDetails.put("path", "/api/departments/" + id);

            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    /* @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable long id) {
        return departmentService.removeDepartment(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable long id) {
        boolean deleted = departmentService.removeDepartment(id);

        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("error", "Department not found");
            response.put("message", "No department found with ID: " + id);
            response.put("path", "/api/departments/" + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
