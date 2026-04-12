package com.fraslab.site.controller;

import com.fraslab.site.common.ApiResponse;
import com.fraslab.site.dto.publicapi.PublicNewsDto;
import com.fraslab.site.dto.publicapi.PublicResearchAreaDto;
import com.fraslab.site.model.LabProfile;
import com.fraslab.site.service.NewsService;
import com.fraslab.site.service.ResearchAreaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {

    private final ResearchAreaService researchAreaService;
    private final NewsService newsService;

    public PublicController(ResearchAreaService researchAreaService, NewsService newsService) {
        this.researchAreaService = researchAreaService;
        this.newsService = newsService;
    }

    @GetMapping("/profile")
    public ApiResponse<LabProfile> profile() {
        LabProfile profile = new LabProfile(
                "FRAS-lab 智能与先进控制实验室",
                "FRAS-lab for Robotics and Automation Systems",
                "面向机器人与智能系统的先进控制研究",
                "Advanced control research for robotic and intelligent systems",
                "contact@fras-lab.example",
                "+86-000-00000000"
        );
        return ApiResponse.ok(profile);
    }

    @GetMapping("/research-areas")
    public ApiResponse<List<PublicResearchAreaDto>> researchAreas() {
        return ApiResponse.ok(researchAreaService.getPublicList());
    }

    @GetMapping("/news")
    public ApiResponse<List<PublicNewsDto>> news() {
        return ApiResponse.ok(newsService.getPublicList());
    }
}
