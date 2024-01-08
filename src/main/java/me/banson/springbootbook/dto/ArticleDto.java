package me.banson.springbootbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.banson.springbootbook.domain.Article;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime updatedAt;
    private String author;

    public ArticleDto(Article article) {
        this.id=article.getId();
        this.title= article.getTitle();
        this.content= article.getContent();
    }
}
