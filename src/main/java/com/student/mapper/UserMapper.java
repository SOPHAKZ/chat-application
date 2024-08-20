package com.student.mapper;

import com.student.dto.RegisterDTO;
import com.student.dto.UserDTO;
import com.student.entity.User;


public interface UserMapper {
    UserDTO userDTO(User user);
    User userEntity(RegisterDTO registerDTO);
    
}
