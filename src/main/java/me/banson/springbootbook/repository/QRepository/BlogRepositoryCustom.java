package me.banson.springbootbook.repository.QRepository;

import me.banson.springbootbook.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepositoryCustom {
    Page<Article> findMyArticle(String name, Pageable pageable, String search);

    void removeFile(Long id);
}
