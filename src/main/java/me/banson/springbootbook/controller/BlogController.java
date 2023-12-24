package me.banson.springbootbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.domain.Article;
import me.banson.springbootbook.dto.AddArticleRequest;
import me.banson.springbootbook.dto.ArticleResponse;
import me.banson.springbootbook.dto.UpdateArticleRequest;
import me.banson.springbootbook.service.BlogService;
import me.banson.springbootbook.service.CommentService;
import me.banson.springbootbook.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final UserService userService;
    private final CommentService commentService;

    //게시물 만들기
    @PostMapping("api/articles")
    @Operation(description = "게시물 작성")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        String name;
        if (principal.getName().contains("@")) {
            name = userService.findByEmail(principal.getName()).getNickname();
        } else {
            name = userService.findByGoogleId(principal.getName()).getNickname();
        }
        Article savedArticle = blogService.save(request, name);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    //게시물 전체 조회
    @GetMapping("api/articles")
    @Operation(description = "게시물 전체 조회")
    public ResponseEntity<Page<ArticleResponse>> findAllArticle(@PageableDefault(sort = "id", size = 5)Pageable pageable, String search) {
        Page<ArticleResponse> articles = (Page<ArticleResponse>) blogService.findByTitleContaining(pageable, search)
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    //게시물 하나 조회
    @GetMapping("api/articles/{id}")
    @Operation(description = "게시물 하나 조회")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable @Parameter(name = "id", description = "게시물 id") long id) {
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    //게시물 삭제
    @DeleteMapping("api/articles/{id}")
    @Operation(description = "게시물 삭제")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);
        commentService.deleteByArticleId(id);

        return ResponseEntity.ok().build();
    }

    //게시물 수정
    @PutMapping("api/articles/{id}")
    @Operation(description = "게시물 수정")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request) {
        Article updateArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updateArticle);
    }
}
