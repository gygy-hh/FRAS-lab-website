package com.fraslab.site.controller;

import com.fraslab.site.common.ApiResponse;
import com.fraslab.site.dto.admin.CarouselImageUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicCarouselImageDto;
import com.fraslab.site.service.CarouselImageService;
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
@RequestMapping("/api/v1/admin/carousel-images")
public class AdminCarouselImageController {

    private final CarouselImageService carouselImageService;

    public AdminCarouselImageController(CarouselImageService carouselImageService) {
        this.carouselImageService = carouselImageService;
    }

    @GetMapping
    public ApiResponse<List<PublicCarouselImageDto>> list() {
        return ApiResponse.ok(carouselImageService.getAdminList());
    }

    @PostMapping
    public ApiResponse<PublicCarouselImageDto> create(@Valid @RequestBody CarouselImageUpsertRequest request) {
        return ApiResponse.ok(carouselImageService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PublicCarouselImageDto> update(@PathVariable Long id, @Valid @RequestBody CarouselImageUpsertRequest request) {
        return ApiResponse.ok(carouselImageService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id) {
        carouselImageService.delete(id);
        return ApiResponse.ok(true);
    }
}
