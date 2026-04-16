package com.fraslab.site.controller;

import com.fraslab.site.common.ApiResponse;
import com.fraslab.site.dto.admin.UploadResponse;
import com.fraslab.site.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/admin/upload")
public class AdminUploadController {

    private final FileStorageService fileStorageService;

    public AdminUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UploadResponse> upload(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "type", defaultValue = "image") String type
    ) throws IOException {
        if (file == null || file.isEmpty()) {
            return ApiResponse.ok(new UploadResponse("", ""));
        }
        validateFileType(type, file.getContentType());
        String fileName = fileStorageService.save(file, type);
        return ApiResponse.ok(new UploadResponse(fileName, "/api/v1/public/files/" + fileName));
    }

    private void validateFileType(String type, String contentType) {
        String actualType = contentType == null ? "" : contentType;
        if ("pdf".equalsIgnoreCase(type) && !"application/pdf".equalsIgnoreCase(actualType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "仅允许上传 PDF");
        }
        if ("image".equalsIgnoreCase(type) && !actualType.toLowerCase().startsWith("image/")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "仅允许上传图片");
        }
    }
}
