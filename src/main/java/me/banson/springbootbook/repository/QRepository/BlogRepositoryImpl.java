package me.banson.springbootbook.repository.QRepository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.banson.springbootbook.domain.QArticle.article;

@RequiredArgsConstructor
@Repository
public class BlogRepositoryImpl implements BlogRepositoryCustom{
    public final JPAQueryFactory jpaQueryFactory;

    public List<Article> findMyTitle(String name) {
        JPAQuery<Article> articles = jpaQueryFactory
                .selectFrom(article)
                .where(article.author.like(name))
                .orderBy(article.content.desc());

        List<Article> articleList = articles.fetch();
        return articleList;
    }
}
