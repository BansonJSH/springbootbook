package me.banson.springbootbook.repository.QRepository;

import me.banson.springbootbook.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepositoryCustom {
    List<Article> findMyArticle(String name, int pageNo, String search);

    Long countMyArticle(String name, String search);

    void removeFile(Long id);
}
