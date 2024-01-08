package me.banson.springbootbook.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidUserRequest {

    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String validNumber;
    private String userValidNumber;

    @Builder
    ValidUserRequest(String email, String password, String nickname, String validNumber) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.validNumber = validNumber;
    }
}
