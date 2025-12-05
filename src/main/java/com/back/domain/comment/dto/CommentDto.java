package com.back.domain.comment.dto;

import com.back.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDto {
    private int id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String content;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.createDate = comment.getCreatedDate();
        this.modifyDate = comment.getModifyDate();
        this.content = comment.getContent();
    }
}
