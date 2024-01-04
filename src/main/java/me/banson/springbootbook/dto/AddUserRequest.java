package me.banson.springbootbook.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
public class AddUserRequest {
    @Email
    @NotEmpty(message = "비어있을 수 없음")
    private String email;
    @NotEmpty(message = "비어있을 수 없음")
    private String password;
    @NotEmpty(message = "비어있을 수 없음")
    private String nickname;

    public AddUserRequest () {
    }

    @Builder
    public AddUserRequest (String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
