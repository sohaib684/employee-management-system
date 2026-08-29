package com.sohaib.employeemanagement.service.impl;

import com.sohaib.employeemanagement.entity.Employee;
import com.sohaib.employeemanagement.dto.EmployeeResponseDto;
import com.sohaib.employeemanagement.repository.EmployeeRepository;
import com.sohaib.employeemanagement.service.FileStorageService;
import com.sohaib.employeemanagement.exception.EmployeeNotFoundException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.sohaib.employeemanagement.dto.EmployeeRequestDto;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void getEmployeeById_shouldThrowException_whenEmployeeNotFound() {

        // Arrange
        when(employeeRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(99L)
        );
    }
    @Test
    void saveEmployee_shouldSaveEmployee() {

        // Arrange
        EmployeeRequestDto request = new EmployeeRequestDto();

        request.setName("Rahul");
        request.setEmail("rahul@example.com");
        request.setDepartment("IT");
        request.setCity("Bangalore");
        request.setSalary(60000.0);

        Employee savedEmployee = new Employee();

        savedEmployee.setId(2L);
        savedEmployee.setName("Rahul");
        savedEmployee.setEmail("rahul@example.com");
        savedEmployee.setDepartment("IT");
        savedEmployee.setCity("Bangalore");
        savedEmployee.setSalary(60000.0);

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(savedEmployee);

        // Act
        EmployeeResponseDto result =
                employeeService.saveEmployee(request);

        // Assert
        assertEquals(2L, result.getId());
        assertEquals("Rahul", result.getName());

        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void getEmployeeById_shouldReturnEmployee() {

        // Arrange
        Employee employee = new Employee();

        employee.setId(1L);
        employee.setName("Sohaib");
        employee.setEmail("sohaib@example.com");
        employee.setDepartment("IT");
        employee.setCity("Bangalore");
        employee.setSalary(50000.0);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        // Act
        EmployeeResponseDto result =
                employeeService.getEmployeeById(1L);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Sohaib", result.getName());
        assertEquals("sohaib@example.com", result.getEmail());
        assertEquals("IT", result.getDepartment());
        assertEquals("Bangalore", result.getCity());
        assertEquals(50000, result.getSalary());
    }
}