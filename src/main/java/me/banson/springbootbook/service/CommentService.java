package me.banson.springbootbook.service;

import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.domain.Comment;
import me.banson.springbootbook.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> findByArticleId(String articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteByArticleId(String articleId) {
        commentRepository.deleteByArticleId(articleId);
    }

    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}
