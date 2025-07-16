package com.gnichi.employee_management.utility;

import com.gnichi.employee_management.entity.Employee;

public class EmployeeValidator {

    /*
    * To handle circular management chains (mutual or indirect cycles) like:
        - A manages B, and B manages A (direct cycle)
        - A manages B, B manages C, and C manages A (indirect cycle)
      => These cycles can cause serious issues in reporting structures, infinite loops in org charts, and even stack overflows in recursive logic.
     */
    public static void validateNoCircularManagement(Employee employee, Employee manager) {
        Employee current = manager;
        while (current != null) {
            if (current.getId().equals(employee.getId())) {
                throw new IllegalArgumentException("Circular management detected");
            }
            current = current.getManager(); // walk up the chain
        }
    }
}

