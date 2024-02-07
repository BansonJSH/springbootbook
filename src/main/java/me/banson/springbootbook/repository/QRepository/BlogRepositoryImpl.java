package me.banson.springbootbook.repository.QRepository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.banson.springbootbook.domain.QArticle.article;

@RequiredArgsConstructor
@Repository
public class BlogRepositoryImpl implements BlogRepositoryCustom {
    public final JPAQueryFactory jpaQueryFactory;

    public Page<Article> findMyArticle(String name, Pageable pageable, String search) {
        QueryResults<Article> articleList = jpaQueryFactory
                .selectFrom(article)
                .where(article.author.eq(name), article.title.contains(search))
                .orderBy(article.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Article> articles = articleList.getResults();
        long total = articleList.getTotal();

        return new PageImpl<>(articles, pageable, total);
    }

    public void removeFile(Long id) {
        jpaQueryFactory
                .update(article)
                .set(article.originalFileName, (String) null)
                .where(article.id.eq(id))
                .execute();
    }
}
