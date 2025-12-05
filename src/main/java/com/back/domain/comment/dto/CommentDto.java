package com.back.domain.comment.dto;

import com.back.domain.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentDto(
        int id,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        String content
) {
    public CommentDto(Comment comment) {
        this(
                comment.getId(),
                comment.getCreatedDate(),
                comment.getModifyDate(),
                comment.getContent()
        );
    }
}
