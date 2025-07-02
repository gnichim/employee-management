package com.gnichi.employee_management.service;

import com.gnichi.employee_management.entity.Department;
import com.gnichi.employee_management.entity.Employee;
import com.gnichi.employee_management.repository.DepartmentRepository;
import com.gnichi.employee_management.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public Employee createEmployee(Employee employee) {
        Long deptId = employee.getDepartment().getId();
        Department department = departmentRepository.findById(deptId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + deptId));
        // Ensures the saved employee has a full Department reference, including the name.
        employee.setDepartment(department);

        if (employee.getManager() != null && employee.getManager().getId() != 0) {
            Long managerId = employee.getManager().getId();
            Employee manager = employeeRepository.findById(managerId)
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found with ID: " + managerId));
            employee.setManager(manager);
        } else {
            employee.setManager(null); // Optional: clear if not provided
        }

        // Prevent Self-Management: prevent someone from being their own manager
        if (employee.getManager() != null && employee.getManager().getId().equals(employee.getId())) {
            throw new IllegalArgumentException("An employee cannot be their own manager.");
        }

        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));
    }

    public Employee getEmployeeByName(String firstname) {
        return employeeRepository.findByFirstname(firstname)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with firstname: " + firstname));
    }

    public List<Employee> fetchEmployeesByDepartmentId(Long id) {
        return employeeRepository.findByDepartmentId(id);
    }

    public Employee updateEmployee(Employee employee) {
        Employee existingEmployee = employeeRepository.findById(employee.getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employee.getId()));

            existingEmployee.setFirstname(employee.getFirstname());
            existingEmployee.setLastname(employee.getLastname());
            existingEmployee.setContractStartDate(employee.getContractStartDate());
            existingEmployee.setContractEndDate(employee.getContractEndDate());
            existingEmployee.setJobTitle(employee.getJobTitle());
            existingEmployee.setGender(employee.getGender());

            return employeeRepository.save(existingEmployee);
    }

    public void removeEmployee(long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }

    public List<Employee> getEmployeesByManagerId(Long managerId) {
        return employeeRepository.findByManagerId(managerId);
    }

}
