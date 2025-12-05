package com.back.domain.post.dto;

import com.back.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private int id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String title;
    private String content;

    public PostDto(Post post){
        this.id = post.getId();
        this.createDate = post.getCreatedDate();
        this.modifyDate = post.getModifyDate();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
