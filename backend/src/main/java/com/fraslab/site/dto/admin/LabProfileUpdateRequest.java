package com.fraslab.site.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LabProfileUpdateRequest(
        @NotBlank @Size(max = 200) String nameZh,
        @NotBlank @Size(max = 200) String nameEn,
        @Size(max = 500) String sloganZh,
        @Size(max = 500) String sloganEn,
        @NotBlank @Size(max = 120) String email,
        @NotBlank @Size(max = 64) String phone,
        @Size(max = 500) String addressZh,
        @Size(max = 500) String addressEn,
        String introZh,
        String introEn
) {
}
