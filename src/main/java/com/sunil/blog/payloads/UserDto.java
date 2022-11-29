package com.sunil.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Integer id;

    @NotEmpty(message = "Name is required")
    @Size(min = 3, message = "Name is not valid")
    private String name;

    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email is required")
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotEmpty(message = "Password is required")
    @Size(min = 6, max = 12, message = "Password length should be between 6 and 12")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$", message = "Password is not valid")
    private String password;

    @Size(max = 255, message = "Limit exceeded for about")
    private String about;

    @JsonProperty(access = Access.READ_ONLY)
    private Set<RoleDto> roles = new HashSet<>();

}
