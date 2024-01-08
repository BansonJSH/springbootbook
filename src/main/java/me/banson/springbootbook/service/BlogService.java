package me.banson.springbootbook.service;

import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.domain.Article;
import me.banson.springbootbook.dto.ArticleDto;
import me.banson.springbootbook.repository.BlogRepository;
import me.banson.springbootbook.repository.QRepository.BlogRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogRepositoryImpl qBlogRepository;

    @Transactional
    public Article save(ArticleDto request, String userName) {
        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(userName)
                .build();
        return blogRepository.save(article);
    }

    public Page<Article> findByTitleContaining(Pageable pageable, String search) {
        int pageNo = pageable.getPageNumber()-1;
        return blogRepository.findByTitleContaining(PageRequest.of(pageNo, 3, Sort.by("createdAt").descending()), search);
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, ArticleDto request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }

    public List<Article> findMyTitle(String name) {
        return qBlogRepository.findMyTitle(name);
    }
}
