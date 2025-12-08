package com.back.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostWriteRequest(
        @NotBlank
        @Size(min = 2, max = 100)
        String title,
        @NotBlank
        @Size(min = 2, max = 5000)
        String content
) {
}
