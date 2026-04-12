package com.fraslab.site.controller;

import com.fraslab.site.common.ApiResponse;
import com.fraslab.site.dto.admin.AdvisorUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicAdvisorDto;
import com.fraslab.site.service.AdvisorService;
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
@RequestMapping("/api/v1/admin/advisors")
public class AdminAdvisorController {

    private final AdvisorService advisorService;

    public AdminAdvisorController(AdvisorService advisorService) {
        this.advisorService = advisorService;
    }

    @GetMapping
    public ApiResponse<List<PublicAdvisorDto>> list() {
        return ApiResponse.ok(advisorService.getAdminList());
    }

    @PostMapping
    public ApiResponse<PublicAdvisorDto> create(@Valid @RequestBody AdvisorUpsertRequest request) {
        return ApiResponse.ok(advisorService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PublicAdvisorDto> update(@PathVariable Long id, @Valid @RequestBody AdvisorUpsertRequest request) {
        return ApiResponse.ok(advisorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id) {
        advisorService.delete(id);
        return ApiResponse.ok(true);
    }
}
