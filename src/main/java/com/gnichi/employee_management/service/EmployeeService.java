package com.gnichi.employee_management.service;

import com.gnichi.employee_management.dto.EmployeeCreateRequest;
import com.gnichi.employee_management.dto.EmployeeSearchRequest;
import com.gnichi.employee_management.entity.Department;
import com.gnichi.employee_management.entity.Employee;
import com.gnichi.employee_management.repository.DepartmentRepository;
import com.gnichi.employee_management.repository.EmployeeRepository;
import com.gnichi.employee_management.specification.EmployeeSpecification;
import com.gnichi.employee_management.utility.EmployeeValidator;
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

    public Employee createEmployee(EmployeeCreateRequest request) {
        // Fetch department
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + request.getDepartmentId()));

        // Create new employee entity
        Employee employee = new Employee();
        employee.setFirstname(request.getFirstname());
        employee.setLastname(request.getLastname());
        employee.setJobTitle(request.getJobTitle());
        employee.setContractStartDate(request.getContractStartDate());
        employee.setContractEndDate(request.getContractEndDate());
        employee.setGender(request.getGender());
        employee.setDepartment(department);

        // Handle manager assignment
        if (request.getManagerId() != null && request.getManagerId() != 0) {
            Employee manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found with ID: " + request.getManagerId()));

            // Validate circular management
            EmployeeValidator.validateNoCircularManagement(employee, manager);

            // Prevent self-management (only relevant if ID is manually set, but safe to keep)
            if (manager.getId().equals(employee.getId())) {
                throw new IllegalArgumentException("An employee cannot be their own manager.");
            }

            employee.setManager(manager);
        } else {
            employee.setManager(null);
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

        if (employee.getManager() != null && employee.getManager().getId() != 0) {
            Long managerId = employee.getManager().getId();
            Employee manager = employeeRepository.findById(managerId)
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found with ID: " + managerId));

            EmployeeValidator.validateNoCircularManagement(existingEmployee, manager);
            existingEmployee.setManager(manager);
        } else {
            existingEmployee.setManager(null);
        }

        return employeeRepository.save(existingEmployee);
    }

    public void removeEmployee(long id) {
        List<Employee> subordinates = employeeRepository.findByManagerId(id);
        if (!subordinates.isEmpty()) {
            throw new IllegalArgumentException("Cannot delete employee who is a manager of other employees.");
        }

        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }

    public List<Employee> getEmployeesByManagerId(Long managerId) {
        return employeeRepository.findByManagerId(managerId);
    }

    public List<Employee> searchEmployees(EmployeeSearchRequest request) {
        return employeeRepository.findAll(EmployeeSpecification.withFilters(request));
    }

}
