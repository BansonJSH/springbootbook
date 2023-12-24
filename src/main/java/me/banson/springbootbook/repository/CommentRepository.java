package me.banson.springbootbook.repository;

import me.banson.springbootbook.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByArticleId(String articleId);

    void deleteByArticleId(Long articleId);
}
