package com.fraslab.site.dto.publicapi;

public record PublicAdvisorDto(
        Long id,
        String code,
        String email,
        String phone,
        String fullNameZh,
        String titleZh,
        String summaryZh,
        String fullNameEn,
        String titleEn,
        String summaryEn
) {
}
