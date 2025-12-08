package com.back.domain.post.controller;

import com.back.domain.post.dto.PostDto;
import com.back.domain.post.entity.Post;
import com.back.domain.post.service.PostService;
import com.back.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public List<PostDto> getItems() {
        List<Post> items = postService.findAll();
        return items.stream()
                .map(PostDto::new) // = post -> new PostDto(post)
                .toList();
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public PostDto getItem(@PathVariable int id) {
        Post post = postService.findById(id).get();
        return new PostDto(post);
    }

    @GetMapping("{id}/delete")
    @Transactional
    public RsData<PostDto> delete(@PathVariable int id) {
        PostDto deletedPost = postService.delete(id);
        return new RsData<>("200-1", "%d번 글이 삭제되었습니다.".formatted(id), deletedPost);
    }
}
