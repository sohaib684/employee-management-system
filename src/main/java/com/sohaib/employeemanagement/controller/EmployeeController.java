package com.sohaib.employeemanagement.controller;

import com.sohaib.employeemanagement.dto.EmployeeRequestDto;
import com.sohaib.employeemanagement.dto.EmployeeResponseDto;
import com.sohaib.employeemanagement.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeResponseDto>> searchEmployees(

            @RequestParam(required = false) String name,

            @RequestParam(required = false) String department,

            @RequestParam(required = false) String city,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                employeeService.searchEmployees(
                        name,
                        department,
                        city,
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }
    // CREATE
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> saveEmployee(
            @Valid @RequestBody EmployeeRequestDto employeeRequestDto) {

        EmployeeResponseDto savedEmployee =
                employeeService.saveEmployee(employeeRequestDto);

        return new ResponseEntity<>(
                savedEmployee,
                HttpStatus.CREATED
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(id)
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<Page<EmployeeResponseDto>> getAllEmployees(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                employeeService.getAllEmployees(
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDto employeeRequestDto) {

        EmployeeResponseDto updatedEmployee =
                employeeService.updateEmployee(
                        id,
                        employeeRequestDto
                );

        return ResponseEntity.ok(updatedEmployee);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }
}