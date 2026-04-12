package com.fraslab.site.controller;

import com.fraslab.site.common.ApiResponse;
import com.fraslab.site.dto.admin.LabProfileUpdateRequest;
import com.fraslab.site.model.LabProfile;
import com.fraslab.site.service.LabProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/lab-profile")
public class AdminLabProfileController {

    private final LabProfileService labProfileService;

    public AdminLabProfileController(LabProfileService labProfileService) {
        this.labProfileService = labProfileService;
    }

    @GetMapping
    public ApiResponse<LabProfile> get() {
        return ApiResponse.ok(labProfileService.getProfile());
    }

    @PutMapping
    public ApiResponse<LabProfile> update(@Valid @RequestBody LabProfileUpdateRequest request) {
        return ApiResponse.ok(labProfileService.update(request));
    }
}
