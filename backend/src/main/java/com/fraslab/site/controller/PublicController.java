package com.fraslab.site.controller;

import com.fraslab.site.common.ApiResponse;
import com.fraslab.site.dto.publicapi.PublicAdvisorDto;
import com.fraslab.site.dto.publicapi.PublicActivityDto;
import com.fraslab.site.dto.publicapi.PublicMemberDto;
import com.fraslab.site.dto.publicapi.PublicNewsDto;
import com.fraslab.site.dto.publicapi.PublicPublicationDto;
import com.fraslab.site.dto.publicapi.PublicResearchAreaDto;
import com.fraslab.site.model.LabProfile;
import com.fraslab.site.service.AdvisorService;
import com.fraslab.site.service.ActivityService;
import com.fraslab.site.service.LabProfileService;
import com.fraslab.site.service.MemberService;
import com.fraslab.site.service.NewsService;
import com.fraslab.site.service.PublicationService;
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
    private final AdvisorService advisorService;
    private final MemberService memberService;
    private final PublicationService publicationService;
    private final ActivityService activityService;
    private final LabProfileService labProfileService;

    public PublicController(
            ResearchAreaService researchAreaService,
            NewsService newsService,
            AdvisorService advisorService,
            MemberService memberService,
            PublicationService publicationService,
            ActivityService activityService,
            LabProfileService labProfileService
    ) {
        this.researchAreaService = researchAreaService;
        this.newsService = newsService;
        this.advisorService = advisorService;
        this.memberService = memberService;
        this.publicationService = publicationService;
        this.activityService = activityService;
        this.labProfileService = labProfileService;
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

    @GetMapping("/advisors")
    public ApiResponse<List<PublicAdvisorDto>> advisors() {
        return ApiResponse.ok(advisorService.getPublicList());
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
}
