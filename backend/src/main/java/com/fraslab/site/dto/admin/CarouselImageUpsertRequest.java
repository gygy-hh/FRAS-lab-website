package com.fraslab.site.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CarouselImageUpsertRequest(
        @NotBlank @Size(max = 512) String imageUrl,
        @NotNull Integer displayOrder,
        @NotNull Boolean active
) {
}
