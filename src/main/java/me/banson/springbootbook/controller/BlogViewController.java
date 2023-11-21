package me.banson.springbootbook.controller;

import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.config.oauth2.OAuth2UserCustomService;
import me.banson.springbootbook.domain.Article;
import me.banson.springbootbook.domain.User;
import me.banson.springbootbook.dto.ArticleListViewResponse;
import me.banson.springbootbook.dto.ArticleViewResponse;
import me.banson.springbootbook.service.BlogService;
import me.banson.springbootbook.service.UserService;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

    private final BlogService blogService;
    private final UserService userService;

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    //전체 게시물 조회
    @GetMapping("/articles")
    public String getArticles(Model model, Principal principal) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new).toList();
        model.addAttribute("articles", articles);

        String name;
        if (isAuthenticated()) {
            if (!principal.getName().contains("@")) {
                System.out.println("실행됨요");
                name = userService.findByGoogleId(principal.getName()).getNickname();
            }
            else {
                name = userService.findByEmail(principal.getName()).getNickname();
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
        model.addAttribute("article", new ArticleViewResponse(article));

        String name;
        if (isAuthenticated()) {
            if (!principal.getName().contains("@")) {
                System.out.println("실행됨요");
                name = userService.findByGoogleId(principal.getName()).getNickname();
            }
            else {
                name = userService.findByEmail(principal.getName()).getNickname();
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
