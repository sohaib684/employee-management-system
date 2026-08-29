package com.sohaib.employeemanagement.service.impl;

import com.sohaib.employeemanagement.entity.Employee;
import com.sohaib.employeemanagement.dto.EmployeeResponseDto;
import com.sohaib.employeemanagement.repository.EmployeeRepository;
import com.sohaib.employeemanagement.service.FileStorageService;

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