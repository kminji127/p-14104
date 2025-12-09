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
