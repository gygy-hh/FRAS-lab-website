package com.fraslab.site.dto.publicapi;

public record PublicMemberDto(
        Long id,
        String code,
        String roleType,
        String email,
        String cvPdfUrl,
        Integer enrollmentYear,
        String fullNameZh,
        String bioZh,
        String fullNameEn,
        String bioEn
) {
}
