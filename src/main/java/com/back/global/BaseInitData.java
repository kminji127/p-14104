package com.back.global;

import com.back.domain.post.entity.Post;
import com.back.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    @Autowired
    @Lazy
    private BaseInitData self;
    private final PostService postService;

    @Bean
    ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        if (postService.count() > 0) return;
        Post post1 = postService.write("제목 1", "내용 1");
        Post post2 = postService.write("제목 2", "내용 2");
        Post post3 = postService.write("제목 3", "내용 3");

        post1.addComment("댓글 1-1");
        post1.addComment("댓글 1-2");
        post1.addComment("댓글 1-3");

        post2.addComment("댓글 2-1");
        post2.addComment("댓글 2-2");
        post2.addComment("댓글 2-3");
    }

    @Transactional
    public void work2() {
        Post postId1 = postService.findById(1).orElse(null);
        if (postId1 != null) {
            if (postId1.getComments().isEmpty()) {
                postId1.addComment("댓글 1-1");
                postId1.addComment("댓글 1-2");
                postId1.addComment("댓글 1-3");
            }
        }

        Post postId2 = postService.findById(2).orElse(null);
        System.out.println(postId2);
        if (postId2 != null) {
            if (postId2.getComments().isEmpty()) {
                postId2.addComment("댓글 2-1");
                postId2.addComment("댓글 2-2");
                postId2.addComment("댓글 2-3");
            }
        }
    }
}
