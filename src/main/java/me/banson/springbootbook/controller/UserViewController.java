package me.banson.springbootbook.controller;

import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.domain.User;
import me.banson.springbootbook.dto.AddUserRequest;
import me.banson.springbootbook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/userInfo")
    public String userInfo(Model model, Principal principal) {
        User user = userService.findUser(principal);
        AddUserRequest userRequest = AddUserRequest.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();

        model.addAttribute("userInfo", userRequest);

        return "userInfo";
    }

    @PostMapping("/userInfo")
    public String userInfo(@ModelAttribute("userInfo") AddUserRequest addUserRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.reject("userInfoGlobal", null);
            return "userInfo";
        }

        userService.changePassword(addUserRequest);

        return "redirect:/articles";
    }
}
