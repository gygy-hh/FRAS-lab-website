package com.fraslab.site.dto.publicapi;

import java.time.LocalDateTime;

public record PublicActivityDto(
        Long id,
        String slug,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String location,
        String imageUrl,
        String titleZh,
        String descriptionZh,
        String titleEn,
        String descriptionEn
) {
}
