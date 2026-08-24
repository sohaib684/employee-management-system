package com.sohaib.employeemanagement.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public String storeFile(Long employeeId, MultipartFile file) throws IOException {
        return null;
    }
}