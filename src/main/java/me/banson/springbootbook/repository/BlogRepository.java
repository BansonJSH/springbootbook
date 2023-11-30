package me.banson.springbootbook.repository;

import me.banson.springbootbook.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Article, Long> {
    Page<Article> findByTitleContaining(Pageable pageable, String search);
}
