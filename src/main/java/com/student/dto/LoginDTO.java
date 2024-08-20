package com.student.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {

    @NotNull(message = "User is required")
    private String username;
    @NotNull(message = "Password is required")
    @Size(min = 5, max = 32, message = "password size should be between 5 and 32 digit or characters")
    private String password;
}
