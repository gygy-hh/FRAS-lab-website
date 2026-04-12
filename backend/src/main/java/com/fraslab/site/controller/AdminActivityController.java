package com.fraslab.site.controller;

import com.fraslab.site.common.ApiResponse;
import com.fraslab.site.dto.admin.ActivityUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicActivityDto;
import com.fraslab.site.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/activities")
public class AdminActivityController {

    private final ActivityService activityService;

    public AdminActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public ApiResponse<List<PublicActivityDto>> list() {
        return ApiResponse.ok(activityService.getAdminList());
    }

    @PostMapping
    public ApiResponse<PublicActivityDto> create(@Valid @RequestBody ActivityUpsertRequest request) {
        return ApiResponse.ok(activityService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PublicActivityDto> update(@PathVariable Long id, @Valid @RequestBody ActivityUpsertRequest request) {
        return ApiResponse.ok(activityService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id) {
        activityService.delete(id);
        return ApiResponse.ok(true);
    }
}
