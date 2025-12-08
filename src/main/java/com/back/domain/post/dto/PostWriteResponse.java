package com.back.domain.post.dto;

public record PostWriteResponse(
        long totalCount,
        PostDto post
) {
}
