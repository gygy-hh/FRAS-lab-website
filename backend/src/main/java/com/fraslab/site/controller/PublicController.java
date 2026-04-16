package com.fraslab.site.controller;

import com.fraslab.site.common.ApiResponse;
import com.fraslab.site.dto.publicapi.PublicActivityDto;
import com.fraslab.site.dto.publicapi.PublicCarouselImageDto;
import com.fraslab.site.dto.publicapi.PublicMemberDto;
import com.fraslab.site.dto.publicapi.PublicNewsDto;
import com.fraslab.site.dto.publicapi.PublicPublicationDto;
import com.fraslab.site.dto.publicapi.PublicResearchAreaDto;
import com.fraslab.site.model.LabProfile;
import com.fraslab.site.service.ActivityService;
import com.fraslab.site.service.CarouselImageService;
import com.fraslab.site.service.FileStorageService;
import com.fraslab.site.service.LabProfileService;
import com.fraslab.site.service.MemberService;
import com.fraslab.site.service.NewsService;
import com.fraslab.site.service.PublicationService;
import com.fraslab.site.service.ResearchAreaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URLConnection;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {

    private final ResearchAreaService researchAreaService;
    private final NewsService newsService;
    private final MemberService memberService;
    private final PublicationService publicationService;
    private final ActivityService activityService;
    private final CarouselImageService carouselImageService;
    private final LabProfileService labProfileService;
    private final FileStorageService fileStorageService;

    public PublicController(
            ResearchAreaService researchAreaService,
            NewsService newsService,
            MemberService memberService,
            PublicationService publicationService,
            ActivityService activityService,
            CarouselImageService carouselImageService,
            LabProfileService labProfileService,
            FileStorageService fileStorageService
    ) {
        this.researchAreaService = researchAreaService;
        this.newsService = newsService;
        this.memberService = memberService;
        this.publicationService = publicationService;
        this.activityService = activityService;
        this.carouselImageService = carouselImageService;
        this.labProfileService = labProfileService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/profile")
    public ApiResponse<LabProfile> profile() {
        return ApiResponse.ok(labProfileService.getProfile());
    }

    @GetMapping("/research-areas")
    public ApiResponse<List<PublicResearchAreaDto>> researchAreas() {
        return ApiResponse.ok(researchAreaService.getPublicList());
    }

    @GetMapping("/news")
    public ApiResponse<List<PublicNewsDto>> news() {
        return ApiResponse.ok(newsService.getPublicList());
    }

    @GetMapping("/members")
    public ApiResponse<List<PublicMemberDto>> members() {
        return ApiResponse.ok(memberService.getPublicList());
    }

    @GetMapping("/publications")
    public ApiResponse<List<PublicPublicationDto>> publications() {
        return ApiResponse.ok(publicationService.getPublicList());
    }

    @GetMapping("/activities")
    public ApiResponse<List<PublicActivityDto>> activities() {
        return ApiResponse.ok(activityService.getPublicList());
    }

    @GetMapping("/carousel-images")
    public ApiResponse<List<PublicCarouselImageDto>> carouselImages() {
        return ApiResponse.ok(carouselImageService.getPublicList());
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<Resource> file(@PathVariable String fileName) throws IOException {
        Resource resource = fileStorageService.loadAsResource(fileName);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        String contentType = URLConnection.guessContentTypeFromName(resource.getFilename());
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
