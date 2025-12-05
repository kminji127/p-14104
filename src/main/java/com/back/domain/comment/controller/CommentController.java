package com.back.domain.comment.controller;

import com.back.domain.comment.dto.CommentDto;
import com.back.domain.comment.entity.Comment;
import com.back.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {
    private final PostService postService;

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

    @GetMapping("{id}/delete")
    @Transactional
    public String delete(@PathVariable int postId,
                         @PathVariable int id) {
        postService.deleteComment(postId, id);
        return "%d번 댓글이 삭제되었습니다.".formatted(id);
    }
}
