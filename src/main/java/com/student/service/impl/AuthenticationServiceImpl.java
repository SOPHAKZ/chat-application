package com.student.service.impl;

import com.student.dto.LoginDTO;
import com.student.dto.AuthenticationResponseDTO;
import com.student.dto.RegisterDTO;
import com.student.entity.User;
import com.student.mapper.PageMapper;
import com.student.mapper.UserMapper;
import com.student.repository.UserRepository;
import com.student.security.JwtService;
import com.student.service.AuthenticationService;
import com.student.utils.PageUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PageMapper pageMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponseDTO register(RegisterDTO request) {
        if (isEmailOrPhoneAlreadyExists(request.getUsername())) {
            throw new EntityExistsException("Email or Phone Number is already exists");
        }
        User user  = userMapper.userEntity(request);
        user.setPassword(passwordEncoder.encode("@123456"));
        User saveUSer = userRepository.save(userMapper.userEntity(request));

        return AuthenticationResponseDTO.builder().token(jwtService.generateToken(saveUSer)).build();
    }

    @Override
    public AuthenticationResponseDTO login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User " + request.getUsername() + " Not Found"));

        return AuthenticationResponseDTO
                .builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    @Override
    public User fetchUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User id : " + userId + " Not Found")
        );
    }

    @Override
    public Page<User> fetchUsers(Map<String, String> param) {
        Pageable pageable = PageUtil.getPageable(param);

        Page<User> userList = userRepository.findAll(pageable);

        return userList;
    }

    @Override
    public User updateUserById(Long userId, RegisterDTO registerDTO) {
        User user = fetchUserById(userId);
        if(user != null){
            user.setUsername(registerDTO.getUsername());
            user.setAge(registerDTO.getAge());
//            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setRole(registerDTO.getRole());
        }

        return userRepository.save(user);

    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    private boolean isEmailOrPhoneAlreadyExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
