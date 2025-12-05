package com.back.domain.post.dto;

import com.back.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private int id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String subject;
    private String body;

    public PostDto(Post post){
        this.id = post.getId();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifyDate();
        this.subject = post.getTitle();
        this.body = post.getContent();
    }
}
