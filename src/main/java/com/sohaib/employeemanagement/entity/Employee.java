package com.sohaib.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String department;

    private String city;

    private Double salary;

    private String profileImageName;

    private String profileImageType;

    private String profileImagePath;
}