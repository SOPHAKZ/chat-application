package com.student.dto;

import com.student.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String username;
    private int age;
    private Role role;
}
