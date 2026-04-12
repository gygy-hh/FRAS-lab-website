package com.fraslab.site.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LabProfileUpdateRequest(
        @NotBlank @Size(max = 200) String nameZh,
        @NotBlank @Size(max = 200) String nameEn,
        @NotBlank @Size(max = 500) String sloganZh,
        @NotBlank @Size(max = 500) String sloganEn,
        @NotBlank @Size(max = 120) String email,
        @NotBlank @Size(max = 64) String phone,
        @NotBlank @Size(max = 500) String addressZh,
        @NotBlank @Size(max = 500) String addressEn,
        @Size(max = 512) String mapLink
) {
}
