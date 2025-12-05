package com.back.domain.post.controller;

import com.back.domain.post.dto.PostDto;
import com.back.domain.post.entity.Post;
import com.back.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // = @Controller + @ResponseBody
@RequestMapping("/api/v1/posts") // 컨트롤러 공통 접두어
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;

    @GetMapping
    public List<PostDto> getItems() {
        List<Post> items = postService.findAll();
        return items.stream()
                .map(post -> new PostDto(post)) // = PostDto::new
                .toList();
    }

    @GetMapping("{id}")
    public PostDto getItem(@PathVariable int id) {
        Post post = postService.findById(id).get();
        return new PostDto(post);
    }
}
