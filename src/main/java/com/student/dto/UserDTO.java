package com.student.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class UserDTO {
    private long id;
    private String username;
    private int age;
    private String role;
}
