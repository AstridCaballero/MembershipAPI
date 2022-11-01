package com.apprentice.repositories;

import com.apprentice.models.Employee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * this interface contains the methods of the model Employee.java
 */

//@ApplicationScoped allows Quarkus to recognise this interface and inject it when called
@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {
    public Employee findByEmployeeId(final String employeeId) {
        return find("employeeId", employeeId).firstResult();
    }
}
