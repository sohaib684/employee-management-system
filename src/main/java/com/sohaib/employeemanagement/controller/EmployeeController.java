package com.sohaib.employeemanagement.controller;

import com.sohaib.employeemanagement.dto.EmployeeRequestDto;
import com.sohaib.employeemanagement.dto.EmployeeResponseDto;
import com.sohaib.employeemanagement.service.EmailService;
import com.sohaib.employeemanagement.service.EmployeeService;
import com.sohaib.employeemanagement.service.FileStorageService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final FileStorageService fileStorageService;
    private final EmailService emailService;

    public EmployeeController(
            EmployeeService employeeService,
            FileStorageService fileStorageService,
            EmailService emailService) {

        this.employeeService = employeeService;
        this.fileStorageService = fileStorageService;
        this.emailService = emailService;
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
    // UPLOAD PROFILE IMAGE
    @PostMapping(
            value = "/{id}/profile-image",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        try {

            employeeService.uploadProfileImage(id, file);

            return ResponseEntity.ok(
                    "Profile image uploaded successfully"
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload profile image: " + e.getMessage());
        }
    }
    // DOWNLOAD PROFILE IMAGE
    @GetMapping("/{id}/profile-image")
    public ResponseEntity<Resource> downloadProfileImage(
            @PathVariable Long id) {

        try {

            Resource resource =
                    fileStorageService.loadFile(id);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
    @PostMapping("/test-email")
    public ResponseEntity<String> testEmail() {

        emailService.sendWelcomeEmail(
                "sohaibalam27022001@gmail.com",
                "Sohaib Alam"
        );

        return ResponseEntity.ok("Email sent successfully");
    }
    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }
}