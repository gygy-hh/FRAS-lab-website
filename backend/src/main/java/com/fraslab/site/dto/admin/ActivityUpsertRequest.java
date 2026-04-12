package com.fraslab.site.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ActivityUpsertRequest(
        @NotBlank @Size(max = 120) String slug,
        @NotNull LocalDateTime startTime,
        LocalDateTime endTime,
        @NotBlank @Size(max = 200) String location,
        @NotNull Boolean active,
        @NotBlank @Size(max = 300) String titleZh,
        @NotBlank @Size(max = 2000) String descriptionZh,
        @NotBlank @Size(max = 300) String titleEn,
        @NotBlank @Size(max = 2000) String descriptionEn
) {
}
