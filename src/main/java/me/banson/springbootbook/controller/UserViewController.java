package me.banson.springbootbook.controller;

import me.banson.springbootbook.dto.AddUserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("signup")
    public String signup() {
        return "signup";
    }
}
