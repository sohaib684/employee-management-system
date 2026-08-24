package com.sohaib.employeemanagement.service;

import org.springframework.core.io.Resource;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;


public interface FileStorageService {

    Resource loadFile(Long employeeId) throws IOException;
    String storeFile(Long employeeId, MultipartFile file) throws IOException;
}