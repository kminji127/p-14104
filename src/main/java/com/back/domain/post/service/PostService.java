package com.back.domain.post.service;

import com.back.domain.comment.entity.Comment;
import com.back.domain.post.dto.PostDto;
import com.back.domain.post.entity.Post;
import com.back.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public long count() {
        return postRepository.count();
    }

    @Transactional
    public Post write(String title, String content) {
        return postRepository.save(new Post(title, content));
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findLatest() {
        return postRepository.findFirstByOrderByIdDesc();
    }

    public Post modify(int id, String title, String content) {
        Post post = postRepository.findById(id).get();
        post.modify(title, content);
        return post;
    }

    public PostDto delete(int id) {
        Post post = postRepository.findById(id).get();
        postRepository.delete(post);
        return new PostDto(post);
    }

    public Comment writeComment(int postId, String content) {
        Post post = postRepository.findById(postId).get();
        // 트랜잭션 끝난 후 수행되어야 하는 더티 체킹 및 여러 작업들을 지금 당장 수행
        // flush하지 않으면 트랜잭션 달아도 댓글이 생성되지 않음 (@Transactional 메서드가 끝날 때 커밋되는데, 이 시점에서는 아직 커밋이 안 되었기 때문)
        Comment comment = post.addComment(content);
        postRepository.flush();
        return comment;
    }

    public List<Comment> findCommentsById(int id) {
        Post post = postRepository.findById(id).get();
        return post.getComments();
    }

    public Comment findCommentByPostIdAndCommentId(int id, int commentId) {
        Post post = postRepository.findById(id).get();
        return post.findCommentById(commentId).get();
    }

    public void addComment(int id, String commentContent) {
        Post post = postRepository.findById(id).get();
        post.addComment(commentContent);
    }

    public boolean deleteComment(int postId, int commentId) {
        Post post = postRepository.findById(postId).get();
        return post.deleteComment(commentId);
    }

    public Comment modifyComment(int postId, int commentId, String content) {
        Post post = postRepository.findById(postId).get();
        Comment comment = post.findCommentById(commentId).get();
        comment.modify(content);
        return comment;
    }
}
