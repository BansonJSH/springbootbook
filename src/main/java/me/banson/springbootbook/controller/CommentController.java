package me.banson.springbootbook.controller;

import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.domain.Comment;
import me.banson.springbootbook.service.CommentService;
import me.banson.springbootbook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/articles/new-comment")
    public String createComment(@RequestParam String  articleId, @RequestParam String comment, Principal principal) {
        System.out.println(comment);
        System.out.println(articleId);
        String name;
        if (principal.getName().contains("@")) {
            name = userService.findByEmail(principal.getName()).getNickname();
        } else {
            name = userService.findByGoogleId(principal.getName()).getNickname();
        }

        Comment comment1 = Comment.builder()
                .comment(comment)
                .articleId(articleId)
                .nickname(name)
                .build();
        commentService.save(comment1);
        return "redirect:/articles/" + articleId;
    }
}
