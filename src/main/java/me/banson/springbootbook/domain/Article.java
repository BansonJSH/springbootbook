package me.banson.springbootbook.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.banson.Main;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Schema(description = "게시물 DAO")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotEmpty(message = "비어있을 수 없음")
    @Size(max = 30)
    private String title;

    @Column(name = "content", nullable = false)
    @NotEmpty(message = "비어있을 수 없음")
    @Size(max = 100)
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "author", nullable = false)
    @NotEmpty(message = "비어있을 수 없음")
    private String author;

    private String originalFileName;

    private String storeFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Article(String title, String content, String author, String originalFileName, String storeFileName, User user) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.originalFileName = originalFileName;
        this.storeFileName = storeFileName;
        this.user = user;
        user.addArticle(this);
    }

    public void update(String title, String content, String originalFileName, String storeFileName) {
        this.title = title;
        this.content = content;
        this.originalFileName = originalFileName;
        this.storeFileName = storeFileName;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
