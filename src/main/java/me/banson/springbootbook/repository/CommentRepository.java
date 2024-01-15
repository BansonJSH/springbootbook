package me.banson.springbootbook.repository;

import me.banson.springbootbook.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    Page<Comment> findByArticleId(Pageable pageable, String articleId);

    void deleteByArticleId(String articleId);

    void deleteById(String id);
}
