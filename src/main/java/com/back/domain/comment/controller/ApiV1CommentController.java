package com.back.domain.comment.controller;

import com.back.domain.comment.dto.CommentDto;
import com.back.domain.comment.dto.CommentModifyRequest;
import com.back.domain.comment.dto.CommentWriteRequest;
import com.back.domain.comment.entity.Comment;
import com.back.domain.post.service.PostService;
import com.back.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
public class ApiV1CommentController {
    private final PostService postService;

    @PostMapping()
    @Transactional
    public RsData<CommentDto> writeComment(@PathVariable int postId,
                                           @Valid @RequestBody CommentWriteRequest commentWriteRequest) {
        Comment createdComment = postService.writeComment(postId, commentWriteRequest.content());
        return new RsData<>(
                "201-1",
                "%d번 댓글이 생성되었습니다.".formatted(createdComment.getId()),
                new CommentDto(createdComment)
        );
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentList(@PathVariable int postId) {
        List<Comment> comments = postService.findCommentsById(postId);
        return comments.stream()
                .map(CommentDto::new) // = comment -> new CommentDto(comment)
                .toList();
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public CommentDto getComment(@PathVariable int postId,
                                 @PathVariable int id) {
        Comment comment = postService.findCommentByPostIdAndCommentId(postId, id);
        return new CommentDto(comment);
    }

    @PutMapping("{id}")
    @Transactional
    public RsData<CommentDto> modify(@PathVariable int postId,
                                     @PathVariable int id,
                                     @Valid @RequestBody CommentModifyRequest commentModifyRequest) {
        Comment comment = postService.modifyComment(postId, id, commentModifyRequest.content());
        return new RsData<>("200-1", "%d번 댓글이 수정되었습니다.".formatted(id), new CommentDto(comment));
    }

    @DeleteMapping("{id}")
    @Transactional
    public RsData<CommentDto> delete(@PathVariable int postId,
                                     @PathVariable int id) {
        Comment comment = postService.findCommentByPostIdAndCommentId(postId, id);
        postService.deleteComment(postId, id);
        return new RsData<>("200-1", "%d번 댓글이 삭제되었습니다.".formatted(id), new CommentDto(comment));
    }

}
