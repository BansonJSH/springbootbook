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
public class BlogRepositoryImpl implements BlogRepositoryCustom {
    public final JPAQueryFactory jpaQueryFactory;

    public List<Article> findMyArticle(String name, int pageNo, String search) {
        List<Article> articleList = jpaQueryFactory
                .selectFrom(article)
                .where(article.author.eq(name), article.title.contains(search))
                .orderBy(article.createdAt.desc())
                .offset(pageNo)
                .limit(3)
                .fetch();
        return articleList;
    }

    public Long countMyArticle(String name, String search) {
        Long totalArticles = jpaQueryFactory
                .select(article)
                .from(article)
                .where(article.author.eq(name), article.title.contains(search))
                .fetchCount();

        return totalArticles;
    }

    public void removeFile(Long id) {
        jpaQueryFactory
                .update(article)
                .set(article.originalFileName, (String) null)
                .where(article.id.eq(id))
                .execute();
    }
}
