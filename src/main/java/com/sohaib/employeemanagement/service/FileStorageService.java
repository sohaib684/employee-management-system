package com.sohaib.employeemanagement.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    String storeFile(Long employeeId, MultipartFile file) throws IOException;
}