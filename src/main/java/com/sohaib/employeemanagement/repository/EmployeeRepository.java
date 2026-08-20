package com.sohaib.employeemanagement.repository;

import com.sohaib.employeemanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    Page<Employee> findByDepartmentIgnoreCase(
            String department,
            Pageable pageable
    );
    Page<Employee> findByCityIgnoreCase(
            String city,
            Pageable pageable
    );
}