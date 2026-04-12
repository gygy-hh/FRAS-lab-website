package com.fraslab.site.dto.publicapi;

import java.time.LocalDateTime;

public record PublicNewsDto(
        Long id,
        String slug,
        LocalDateTime publishedAt,
        String titleZh,
        String titleEn
) {
}
