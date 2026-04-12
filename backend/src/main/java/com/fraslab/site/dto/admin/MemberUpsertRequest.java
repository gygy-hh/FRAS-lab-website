package com.fraslab.site.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MemberUpsertRequest(
        @NotBlank @Size(max = 64) String code,
        @NotBlank @Size(max = 64) String roleType,
        @Size(max = 120) String email,
        Integer enrollmentYear,
        @NotNull Boolean active,
        @NotBlank @Size(max = 120) String fullNameZh,
        @NotBlank @Size(max = 1200) String bioZh,
        @NotBlank @Size(max = 120) String fullNameEn,
        @NotBlank @Size(max = 1200) String bioEn
) {
}
