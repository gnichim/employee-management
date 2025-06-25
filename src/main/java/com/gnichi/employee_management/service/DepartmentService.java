package com.gnichi.employee_management.service;

import com.gnichi.employee_management.entity.Department;
import com.gnichi.employee_management.repository.DepartmentRepository;
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
        return this.departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return this.departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(long id) {
        return departmentRepository.findById(id);
    }

    public Optional<Department> getDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    public Optional<Department> updateDepartment(Department department) {
        return departmentRepository.findById(department.getId()).map(
                existingDepartment -> {
                    existingDepartment.setName(department.getName());
                    return departmentRepository.save(existingDepartment);
                }
        );
    }

    public boolean removeDepartment(long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
