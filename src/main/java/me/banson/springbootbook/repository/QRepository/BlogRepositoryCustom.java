package me.banson.springbootbook.repository.QRepository;

import me.banson.springbootbook.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogRepositoryCustom {
    List<Article> findMyTitle(String name);
}
