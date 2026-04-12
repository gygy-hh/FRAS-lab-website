package com.fraslab.site.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdvisorUpsertRequest(
        @NotBlank @Size(max = 64) String code,
        @NotBlank @Size(max = 120) String email,
        @Size(max = 64) String phone,
        @NotNull Boolean active,
        @NotBlank @Size(max = 120) String fullNameZh,
        @NotBlank @Size(max = 120) String titleZh,
        @NotBlank @Size(max = 1200) String summaryZh,
        @NotBlank @Size(max = 120) String fullNameEn,
        @NotBlank @Size(max = 120) String titleEn,
        @NotBlank @Size(max = 1200) String summaryEn
) {
}
