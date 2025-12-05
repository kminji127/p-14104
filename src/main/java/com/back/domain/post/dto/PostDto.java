package com.back.domain.post.dto;

import com.back.domain.post.entity.Post;

import java.time.LocalDateTime;

public record PostDto(
        int id,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        String title,
        String content
) {
    public PostDto(Post post) {
        this(
                post.getId(),
                post.getCreatedDate(),
                post.getModifyDate(),
                post.getTitle(),
                post.getContent()
        );
    }
}
