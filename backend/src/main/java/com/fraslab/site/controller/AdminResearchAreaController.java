package com.fraslab.site.controller;

import com.fraslab.site.common.ApiResponse;
import com.fraslab.site.dto.admin.ResearchAreaUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicResearchAreaDto;
import com.fraslab.site.service.ResearchAreaService;
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
@RequestMapping("/api/v1/admin/research-areas")
public class AdminResearchAreaController {

    private final ResearchAreaService researchAreaService;

    public AdminResearchAreaController(ResearchAreaService researchAreaService) {
        this.researchAreaService = researchAreaService;
    }

    @GetMapping
    public ApiResponse<List<PublicResearchAreaDto>> list() {
        return ApiResponse.ok(researchAreaService.getAdminList());
    }

    @PostMapping
    public ApiResponse<PublicResearchAreaDto> create(@Valid @RequestBody ResearchAreaUpsertRequest request) {
        return ApiResponse.ok(researchAreaService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PublicResearchAreaDto> update(@PathVariable Long id, @Valid @RequestBody ResearchAreaUpsertRequest request) {
        return ApiResponse.ok(researchAreaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id) {
        researchAreaService.delete(id);
        return ApiResponse.ok(true);
    }
}
