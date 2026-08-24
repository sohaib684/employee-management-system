package com.sohaib.employeemanagement.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public String storeFile(Long employeeId, MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(
                "uploads",
                "employees",
                employeeId.toString()
        );

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = file.getOriginalFilename();

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return filePath.toString();
    }
    @Override
    public Resource loadFile(Long employeeId) throws IOException {

        Path uploadPath = Paths.get(
                "uploads",
                "employees",
                employeeId.toString()
        );

        if (!Files.exists(uploadPath)) {
            throw new IOException(
                    "Employee upload directory not found"
            );
        }

        try {

            Path filePath = Files.list(uploadPath)
                    .findFirst()
                    .orElseThrow(() ->
                            new IOException(
                                    "Profile image not found"
                            )
                    );

            Resource resource =
                    new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new IOException(
                        "Profile image is not readable"
                );
            }

            return resource;

        } catch (IOException e) {
            throw e;
        }
    }
}