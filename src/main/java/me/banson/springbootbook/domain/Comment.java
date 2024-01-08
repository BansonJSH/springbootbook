package me.banson.springbootbook.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Document(collection = "comment")
@Getter
public class Comment {
    @Id
    private String id;

    private String comment;
    private String nickname;
    private String  articleId;
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Comment(String articleId, String comment, String nickname) {
        this.articleId = articleId;
        this.comment = comment;
        this.nickname = nickname;
    }
}
