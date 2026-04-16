package com.fraslab.site.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path storageRoot;

    public FileStorageService(@Value("${app.upload-dir:uploads}") String uploadDir) throws IOException {
        this.storageRoot = Path.of(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(storageRoot);
    }

    public String save(MultipartFile file, String category) throws IOException {
        String originalName = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename();
        String ext = getExt(originalName);
        String safeName = category + "_" + UUID.randomUUID().toString().replace("-", "") + ext;
        Path target = storageRoot.resolve(safeName).normalize();
        if (!target.startsWith(storageRoot)) {
            throw new IOException("非法文件路径");
        }
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return safeName;
    }

    public Resource loadAsResource(String filename) throws MalformedURLException {
        Path file = storageRoot.resolve(filename).normalize();
        if (!file.startsWith(storageRoot)) {
            throw new MalformedURLException("非法文件路径");
        }
        return new UrlResource(file.toUri());
    }

    private String getExt(String fileName) {
        int idx = fileName.lastIndexOf('.');
        if (idx < 0) {
            return "";
        }
        return fileName.substring(idx);
    }
}
