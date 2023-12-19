package me.banson.springbootbook.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.banson.springbootbook.dto.AddUserRequest;
import me.banson.springbootbook.dto.ValidUserRequest;
import me.banson.springbootbook.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")   //회원가입 완료 후 가입메일 발송
    public String signup(ValidUserRequest request, Model model) {
        if (request.getValidNumber().equals(request.getUserValidNumber())) {
            AddUserRequest request1 = AddUserRequest.builder()
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .nickname(request.getNickname())
                    .build();
            userService.save(request1);
            userService.sendMail(request1);
        } else {
            model.addAttribute("user", request);
            model.addAttribute("validNumber", request.getValidNumber());
            return "validUser";
        }
        return "redirect:/login";
    }

    @PostMapping("/validUser")  //회원가입 요청 시 인증번호 발송
    public String validUser(@Validated AddUserRequest request, Errors errors, Model model) throws Exception {
        if (errors.hasErrors()) {
            model.addAttribute("fail" ,"잘못된 회원가입입니다.");
            return "signup";
        } else {
            String validNumber = userService.sendValidNumber(request);
            model.addAttribute("user" ,request);
            model.addAttribute("validNumber", validNumber);
        }
        return "validUser";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
