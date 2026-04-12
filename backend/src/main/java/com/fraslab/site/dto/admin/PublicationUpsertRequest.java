package com.fraslab.site.dto.admin;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PublicationUpsertRequest(
        @NotBlank @Size(max = 120) String slug,
        @NotNull @Min(1990) @Max(2100) Integer pubYear,
        @NotNull LocalDate pubDate,
        @NotBlank @Size(max = 32) String category,
        @Size(max = 512) String externalUrl,
        @NotNull Boolean active,
        @NotBlank @Size(max = 300) String titleZh,
        @NotBlank @Size(max = 2000) String authorsZh,
        @NotBlank @Size(max = 3000) String abstractZh,
        @NotBlank @Size(max = 300) String titleEn,
        @NotBlank @Size(max = 2000) String authorsEn,
        @NotBlank @Size(max = 3000) String abstractEn
) {
}
