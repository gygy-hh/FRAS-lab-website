package com.fraslab.site.controller;

import com.fraslab.site.common.ApiResponse;
import com.fraslab.site.dto.admin.NewsUpsertRequest;
import com.fraslab.site.dto.publicapi.PublicNewsDto;
import com.fraslab.site.service.NewsService;
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
@RequestMapping("/api/v1/admin/news")
public class AdminNewsController {

    private final NewsService newsService;

    public AdminNewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ApiResponse<List<PublicNewsDto>> list() {
        return ApiResponse.ok(newsService.getAdminList());
    }

    @PostMapping
    public ApiResponse<PublicNewsDto> create(@Valid @RequestBody NewsUpsertRequest request) {
        return ApiResponse.ok(newsService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PublicNewsDto> update(@PathVariable Long id, @Valid @RequestBody NewsUpsertRequest request) {
        return ApiResponse.ok(newsService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id) {
        newsService.delete(id);
        return ApiResponse.ok(true);
    }
}
