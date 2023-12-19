package me.banson.springbootbook.repository;

import me.banson.springbootbook.domain.Article;
import me.banson.springbootbook.repository.QRepository.BlogRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Article, Long>, BlogRepositoryCustom {

    Page<Article> findByTitleContainingDesc(Pageable pageable, String search);
}
