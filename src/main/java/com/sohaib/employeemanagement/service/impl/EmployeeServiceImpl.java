package com.sohaib.employeemanagement.service.impl;

import com.sohaib.employeemanagement.dto.EmployeeRequestDto;
import com.sohaib.employeemanagement.dto.EmployeeResponseDto;
import com.sohaib.employeemanagement.entity.Employee;
import com.sohaib.employeemanagement.exception.EmployeeNotFoundException;
import com.sohaib.employeemanagement.repository.EmployeeRepository;
import com.sohaib.employeemanagement.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private static final Logger log =
            LoggerFactory.getLogger(EmployeeServiceImpl.class);


    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // =========================
    // CREATE EMPLOYEE
    // =========================

    @Override
    public EmployeeResponseDto saveEmployee(EmployeeRequestDto employeeRequestDto) {

        log.info("Creating employee with email: {}",
                employeeRequestDto.getEmail());

        Employee employee = new Employee();

        employee.setName(employeeRequestDto.getName());
        employee.setEmail(employeeRequestDto.getEmail());
        employee.setDepartment(employeeRequestDto.getDepartment());
        employee.setCity(employeeRequestDto.getCity());
        employee.setSalary(employeeRequestDto.getSalary());

        Employee savedEmployee = employeeRepository.save(employee);

        log.info("Employee created successfully with id: {}",
                savedEmployee.getId());

        return convertToResponseDto(savedEmployee);
    }


    // =========================
    // GET EMPLOYEE BY ID
    // =========================

    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {

        log.info("Fetching employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        log.info("Employee found with id: {}", id);

        return convertToResponseDto(employee);
    }


    // =========================
    // GET ALL EMPLOYEES
    // =========================

    @Override
    public Page<EmployeeResponseDto> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String direction) {

        log.info(
                "Fetching employees - page: {}, size: {}, sortBy: {}, direction: {}",
                page, size, sortBy, direction
        );

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Page<Employee> employees =
                employeeRepository.findAll(
                        PageRequest.of(page, size, sort)
                );

        log.info("Fetched {} employees", employees.getNumberOfElements());

        return employees.map(this::convertToResponseDto);
    }


    // =========================
    // UPDATE EMPLOYEE
    // =========================

    @Override
    public EmployeeResponseDto updateEmployee(
            Long id,
            EmployeeRequestDto employeeRequestDto) {

        log.info("Updating employee with id: {}", id);

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        existingEmployee.setName(employeeRequestDto.getName());
        existingEmployee.setEmail(employeeRequestDto.getEmail());
        existingEmployee.setDepartment(employeeRequestDto.getDepartment());
        existingEmployee.setCity(employeeRequestDto.getCity());
        existingEmployee.setSalary(employeeRequestDto.getSalary());

        Employee updatedEmployee =
                employeeRepository.save(existingEmployee);

        log.info("Employee updated successfully with id: {}", id);

        return convertToResponseDto(updatedEmployee);
    }


    // =========================
    // DELETE EMPLOYEE
    // =========================

    @Override
    public void deleteEmployee(Long id) {

        log.info("Deleting employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        employeeRepository.delete(employee);

        log.info("Employee deleted successfully with id: {}", id);
    }
    @Override
    public Page<EmployeeResponseDto> searchEmployees(

            String name,
            String department,
            String city,
            int page,
            int size,
            String sortBy,
            String direction) {
        log.info(
                "Searching employees - name: {}, department: {}, city: {} ,page: {}, size: {}",
                name, department, city , page, size
        );

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageable =
                PageRequest.of(page, size, sort);

        Page<Employee> employees;

        if (name != null && !name.isBlank()) {

            employees =
                    employeeRepository.findByNameContainingIgnoreCase(
                            name,
                            pageable
                    );

        } else if (department != null && !department.isBlank()) {

            employees =
                    employeeRepository.findByDepartmentIgnoreCase(
                            department,
                            pageable
                    );

        }
        else if (city != null && !city.isBlank()) {

            employees = employeeRepository
                    .findByCityIgnoreCase(city, pageable);

        }
        else {

            employees =
                    employeeRepository.findAll(pageable);
        }

        return employees.map(this::convertToResponseDto);
    }

    // =========================
    // ENTITY → RESPONSE DTO
    // =========================

    private EmployeeResponseDto convertToResponseDto(Employee employee) {

        EmployeeResponseDto dto = new EmployeeResponseDto();

        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());
        dto.setCity(employee.getCity());
        dto.setSalary(employee.getSalary());

        return dto;
    }
}