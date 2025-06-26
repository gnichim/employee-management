package com.gnichi.employee_management.service;

import com.gnichi.employee_management.entity.Department;
import com.gnichi.employee_management.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;


    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department createDepartment(Department department) {
        Optional<Department> existingDepartment = departmentRepository.findByNameIgnoreCase(department.getName());
        if (existingDepartment.isPresent()) {
            throw new IllegalArgumentException("Department with name '" + department.getName() + "' already exists");
        }
        return this.departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return this.departmentRepository.findAll();
    }

    public Department getDepartmentById(long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + id));
    }

    public Department getDepartmentByName(String name) {
        return departmentRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with name: " + name));
    }

    public Department updateDepartment(Department department) {
        Department existingDepartment = departmentRepository.findById(department.getId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + department.getId()));

        existingDepartment.setName(department.getName());
        return departmentRepository.save(existingDepartment);
    }

    public void removeDepartment(long id) {
        if (!departmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Department not found with ID: " + id);
        }
        departmentRepository.deleteById(id);
    }
}
