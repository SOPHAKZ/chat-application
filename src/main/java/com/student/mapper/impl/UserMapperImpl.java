package com.student.mapper.impl;

import com.student.dto.RegisterDTO;
import com.student.dto.UserDTO;
import com.student.entity.User;
import com.student.enums.Role;
import com.student.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDTO userDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .age(user.getAge())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public User userEntity(RegisterDTO registerDTO) {
        return User
                .builder()
                .username(registerDTO.getUsername())
                .role(Role.USER)
                .age(registerDTO.getAge())
//                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .build();
    }
}
