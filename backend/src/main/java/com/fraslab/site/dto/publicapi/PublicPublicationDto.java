package com.fraslab.site.dto.publicapi;

import java.time.LocalDate;

public record PublicPublicationDto(
        Long id,
        String slug,
        Integer pubYear,
        LocalDate pubDate,
        String category,
        String externalUrl,
        String titleZh,
        String authorsZh,
        String abstractZh,
        String titleEn,
        String authorsEn,
        String abstractEn
) {
}
