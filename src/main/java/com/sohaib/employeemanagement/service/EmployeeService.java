package com.sohaib.employeemanagement.service;

import com.sohaib.employeemanagement.dto.EmployeeRequestDto;
import com.sohaib.employeemanagement.dto.EmployeeResponseDto;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EmployeeService {

 EmployeeResponseDto saveEmployee(EmployeeRequestDto employeeRequestDto);

 EmployeeResponseDto getEmployeeById(Long id);

 Page<EmployeeResponseDto> getAllEmployees(
         int page,
         int size,
         String sortBy,
         String direction
 );

 Page<EmployeeResponseDto> searchEmployees(
         String name,
         String department,
         String city,
         int page,
         int size,
         String sortBy,
         String direction
 );

 EmployeeResponseDto updateEmployee(
         Long id,
         EmployeeRequestDto employeeRequestDto
 );

 void deleteEmployee(Long id);

 // =========================
 // UPLOAD PROFILE IMAGE
 // =========================

 void uploadProfileImage(
         Long employeeId,
         MultipartFile file
 ) throws IOException;
}