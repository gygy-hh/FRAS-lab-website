package com.fraslab.site.controller;

import com.fraslab.site.common.ApiResponse;
import com.fraslab.site.model.LabProfile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {

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
    public ApiResponse<List<Map<String, String>>> researchAreas() {
        List<Map<String, String>> areas = List.of(
                Map.of("key", "vision", "nameZh", "影像与视觉", "nameEn", "Vision"),
                Map.of("key", "rehab", "nameZh", "复健机器人", "nameEn", "Rehabilitation Robotics"),
                Map.of("key", "uav", "nameZh", "多无人机系统控制", "nameEn", "Multi-UAV Control")
        );
        return ApiResponse.ok(areas);
    }
}
