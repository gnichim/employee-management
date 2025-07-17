package com.gnichi.employee_management.specification;

import com.gnichi.employee_management.dto.EmployeeSearchRequest;
import com.gnichi.employee_management.entity.Employee;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<Employee> withFilters(EmployeeSearchRequest req) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (req.getFirstname() != null) {
                predicates.add(cb.like(cb.lower(root.get("firstname")), "%" + req.getFirstname().toLowerCase() + "%"));
            }

            if (req.getLastname() != null) {
                predicates.add(cb.like(cb.lower(root.get("lastname")), "%" + req.getLastname().toLowerCase() + "%"));
            }

            if (req.getJobTitle() != null) {
                predicates.add(cb.like(cb.lower(root.get("jobTitle")), "%" + req.getJobTitle().toLowerCase() + "%"));
            }

            if (req.getManagerId() != null) {
                predicates.add(cb.equal(root.get("manager").get("id"), req.getManagerId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

