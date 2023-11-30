package me.banson.springbootbook.service;

import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.domain.Comment;
import me.banson.springbootbook.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> findByArticleId(String articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}
