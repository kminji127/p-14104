package com.back.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentWriteRequest(
        @NotBlank
        @Size(min = 2, max = 100)
        String content
) {
}
