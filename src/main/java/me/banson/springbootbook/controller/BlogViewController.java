package me.banson.springbootbook.controller;

import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.domain.Article;
import me.banson.springbootbook.domain.Comment;
import me.banson.springbootbook.dto.ArticleViewResponse;
import me.banson.springbootbook.service.BlogService;
import me.banson.springbootbook.service.CommentService;
import me.banson.springbootbook.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

    private final BlogService blogService;
    private final UserService userService;
    private final CommentService commentService;

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    //전체 게시물 조회
    @GetMapping("/articles")
    public String getArticles(Model model, Principal principal,@PageableDefault(sort = "id", size = 3, page = 1) Pageable pageable,
                              @RequestParam(required = false, defaultValue = "") String search) {
        Page<Article> articles = blogService.findByTitleContaining(pageable, search);
        model.addAttribute("articles", articles);
        int nowPage = articles.getPageable().getPageNumber();
        int firstPage = 0;
        int lastPage = articles.getTotalPages()-1;

        if (nowPage - 4 >= 0) {
            firstPage = nowPage - 4;
        }
        else if (nowPage + 4 <= articles.getTotalPages()-1) {
            lastPage = nowPage + 4;
        }

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("search", search);

        String name;
        if (isAuthenticated()) {
            if (principal.getName().contains("@")) {
                name = userService.findByEmail(principal.getName()).getNickname();
            } else {
                name = userService.findByGoogleId(principal.getName()).getNickname();
            }
            System.out.println(name);
            model.addAttribute("name", name);
        } else {
            model.addAttribute("name", "null");
        }
        return "articleList";
    }

    //게시물 하나 조회
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model, Principal principal) {
        Article article = blogService.findById(id);
        model.addAttribute("article", article);
        List<Comment> comments = commentService.findByArticleId(String.valueOf(id));
        model.addAttribute("comments", comments);

        String name;
        if (isAuthenticated()) {
            if (principal.getName().contains("@")) {
                name = userService.findByEmail(principal.getName()).getNickname();
            } else {
                name = userService.findByGoogleId(principal.getName()).getNickname();
            }
            System.out.println(name);
            model.addAttribute("name", name);
        } else {
            model.addAttribute("name", "null");
        }

        return "article";
    }


    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
