package com.student.service;

import com.student.dto.LoginDTO;
import com.student.dto.AuthenticationResponseModel;
import com.student.dto.RegisterDTO;
import com.student.dto.UserDTO;
import com.student.entity.User;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface AuthenticationService {
    AuthenticationResponseModel register(RegisterDTO request);
    AuthenticationResponseModel login(LoginDTO loginDTO);
    User fetchUserById(Long userId);
    Page<User> fetchUsers(Map<String,String> param);
    User updateUserById(Long userId, RegisterDTO registerDTO);
    void deleteUserById(Long userId);

}
