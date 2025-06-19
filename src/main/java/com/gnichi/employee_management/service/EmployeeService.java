package com.gnichi.employee_management.service;

import com.gnichi.employee_management.entity.Employee;
import com.gnichi.employee_management.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getEmployeeByName(String firstname) {
        return employeeRepository.findByFirstname(firstname);
    }

    public Optional<Employee> updateEmployee(Employee employee) {
        return employeeRepository.findById(employee.getId()).map(existingEmployee -> {
            existingEmployee.setFirstname(employee.getFirstname());
            existingEmployee.setLastname(employee.getLastname());
            existingEmployee.setContractStartDate(employee.getContractStartDate());
            existingEmployee.setContractEndDate(employee.getContractEndDate());
            existingEmployee.setJobTitle(employee.getJobTitle());
            existingEmployee.setGender(employee.getGender());
            return employeeRepository.save(existingEmployee);
        });
    }

    public boolean removeEmployee(long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
