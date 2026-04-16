package com.fraslab.site.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ResearchAreaUpsertRequest(
        @NotBlank @Size(max = 64) String key,
        @NotNull Integer displayOrder,
        @NotNull Boolean active,
        @NotBlank @Size(max = 200) String nameZh,
        @NotBlank String summaryZh,
        @NotBlank @Size(max = 200) String nameEn,
        @NotBlank String summaryEn
) {
}
