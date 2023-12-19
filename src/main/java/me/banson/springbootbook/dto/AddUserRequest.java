package me.banson.springbootbook.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    @Email
    @NotEmpty(message = "비어있을 수 없음")
    private String email;
    @NotEmpty
    @NotEmpty(message = "비어있을 수 없음")
    private String password;
    @NotEmpty
    @NotEmpty(message = "비어있을 수 없음")
    private String nickname;

    @Builder
    public AddUserRequest (String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
