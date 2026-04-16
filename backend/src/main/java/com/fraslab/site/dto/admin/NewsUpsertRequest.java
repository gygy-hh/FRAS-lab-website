package com.fraslab.site.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record NewsUpsertRequest(
        @NotBlank @Size(max = 120) String slug,
        @NotBlank @Size(max = 20) String status,
        @NotNull LocalDateTime publishedAt,
        @Size(max = 512) String imageUrl,
        @NotBlank @Size(max = 250) String titleZh,
        @NotBlank String contentZh,
        @NotBlank @Size(max = 250) String titleEn,
        @NotBlank String contentEn
) {
}
