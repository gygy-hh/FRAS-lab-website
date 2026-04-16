package com.fraslab.site.dto.publicapi;

public record PublicCarouselImageDto(
        Long id,
        String imageUrl,
        Integer displayOrder,
        Boolean active
) {
}
