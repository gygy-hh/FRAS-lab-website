package com.fraslab.site.dto.publicapi;

public record PublicResearchAreaDto(
        Long id,
        String key,
        Integer displayOrder,
        String nameZh,
        String summaryZh,
        String nameEn,
        String summaryEn
) {
}
