package com.fraslab.site.controller;

import com.fraslab.site.common.ApiResponse;
import com.fraslab.site.dto.admin.PublicationUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicPublicationDto;
import com.fraslab.site.service.PublicationService;
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
@RequestMapping("/api/v1/admin/publications")
public class AdminPublicationController {

    private final PublicationService publicationService;

    public AdminPublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping
    public ApiResponse<List<PublicPublicationDto>> list() {
        return ApiResponse.ok(publicationService.getAdminList());
    }

    @PostMapping
    public ApiResponse<PublicPublicationDto> create(@Valid @RequestBody PublicationUpsertRequest request) {
        return ApiResponse.ok(publicationService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PublicPublicationDto> update(@PathVariable Long id, @Valid @RequestBody PublicationUpsertRequest request) {
        return ApiResponse.ok(publicationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id) {
        publicationService.delete(id);
        return ApiResponse.ok(true);
    }
}
