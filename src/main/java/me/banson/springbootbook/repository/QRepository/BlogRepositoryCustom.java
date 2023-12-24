package me.banson.springbootbook.repository.QRepository;

import me.banson.springbootbook.domain.Article;

import java.util.List;

public interface BlogRepositoryCustom {
    List<Article> findMyTitle(String name);
}
