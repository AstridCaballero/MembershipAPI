package com.apprentice.service;

import com.apprentice.models.Employee;
import com.apprentice.repositories.EmployeeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Implementing business logic
 */

//@ApplicationScoped allows Quarkus to recognise this interface and inject it when called
@ApplicationScoped
@Transactional
public class EmployeeService {

    @Inject
    EmployeeRepository employeeRepository;

    /**
     * Stores Employee information
     */

    public void registerEmployee(final Employee employee) {
        employeeRepository.persist(employee);
    }

    /**
     * Returns Employee information by employeeId
     */

    public Employee findEmployee(final String employeeId) {
        return employeeRepository.findByEmployeeId(employeeId);
    }
}
