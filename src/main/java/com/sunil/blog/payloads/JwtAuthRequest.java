package com.sunil.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class JwtAuthRequest {
    @NotEmpty(message = "Email cannot be empty or null")
    @Email(message = "Provide a valid email")
    private String email;

    @NotEmpty(message = "Password cannot be empty or null")
    @Size(min = 6, max = 12, message = "Password length should be between 6 and 12")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$", message = "Password is not valid")
    private String password;
}
