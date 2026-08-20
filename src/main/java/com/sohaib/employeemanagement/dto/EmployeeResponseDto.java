package com.sohaib.employeemanagement.dto;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeResponseDto {

    private Long id;

    private String name;

    private String email;

    private String department;

    private String city;

    private Double salary;

}