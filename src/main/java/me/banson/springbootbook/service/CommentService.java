package me.banson.springbootbook.service;

import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.domain.Comment;
import me.banson.springbootbook.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Page<Comment> findByArticleId(Pageable pageable, String articleId) {
        return commentRepository.findByArticleId(PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize(), Sort.by("id").descending()), articleId);
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
