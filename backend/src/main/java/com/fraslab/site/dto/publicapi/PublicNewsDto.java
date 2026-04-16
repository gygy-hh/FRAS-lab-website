package com.fraslab.site.dto.publicapi;

import java.time.LocalDateTime;

public record PublicNewsDto(
        Long id,
        String slug,
        String status,
        LocalDateTime publishedAt,
        String imageUrl,
        String titleZh,
        String contentZh,
        String titleEn,
        String contentEn
) {
}
