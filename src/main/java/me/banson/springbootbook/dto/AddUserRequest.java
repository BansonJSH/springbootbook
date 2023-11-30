package me.banson.springbootbook.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String nickname;
}
