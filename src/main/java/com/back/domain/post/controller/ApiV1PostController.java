package com.back.domain.post.controller;

import com.back.domain.post.dto.PostDto;
import com.back.domain.post.dto.PostModifyRequest;
import com.back.domain.post.dto.PostWriteRequest;
import com.back.domain.post.entity.Post;
import com.back.domain.post.service.PostService;
import com.back.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // = @Controller + @ResponseBody
@RequestMapping("/api/v1/posts") // 컨트롤러 공통 접두어
@RequiredArgsConstructor
@Tag(name = "ApiV1PostController", description = "API 글 컨트롤러")
public class ApiV1PostController {
    private final PostService postService;

    @PostMapping()
    @Transactional
    @Operation(summary = "작성")
    // @RequestBody: 클라이언트가 보낸 JSON 데이터를 Java 객체로 자동 매핑
    public RsData<PostDto> writePost(@Valid @RequestBody PostWriteRequest postWriteRequest) {
        Post createdPost = postService.write(postWriteRequest.title(), postWriteRequest.content());
        return new RsData<>(
                "201-1",
                "%d번 글이 생성되었습니다.".formatted(createdPost.getId()),
                new PostDto(createdPost)
        );
    }

    @GetMapping
    @Transactional(readOnly = true)
    @Operation(summary = "다건 조회")
    public List<PostDto> getItems() {
        List<Post> items = postService.findAll();
        return items.stream()
                .map(PostDto::new) // = post -> new PostDto(post)
                .toList();
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    @Operation(summary = "단건 조회")
    public PostDto getItem(@PathVariable int id) {
        Post post = postService.findById(id).get();
        return new PostDto(post);
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "수정")
    public RsData<PostDto> modify(@PathVariable int id,
                                  @Valid @RequestBody PostModifyRequest postModifyRequest) {
        Post updatedPost = postService.modify(id, postModifyRequest.title(), postModifyRequest.content());
        return new RsData<>("200-1", "%d번 글이 수정되었습니다.".formatted(id), new PostDto(updatedPost));
    }

    @DeleteMapping("{id}")
    @Transactional
    @Operation(summary = "삭제")
    public RsData<PostDto> delete(@PathVariable int id) {
        PostDto deletedPost = postService.delete(id);
        return new RsData<>("200-1", "%d번 글이 삭제되었습니다.".formatted(id), deletedPost);
    }
}
