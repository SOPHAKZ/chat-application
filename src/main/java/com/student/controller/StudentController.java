package com.student.controller;

import com.student.dto.RegisterDTO;
import com.student.entity.User;

import com.student.exception.ResponseModel;
import com.student.mapper.PageMapper;
import com.student.mapper.UserMapper;
import com.student.mapper.impl.UserMapperImpl;
import com.student.service.AuthenticationService;
import com.student.utils.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class StudentController {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;
    private final PageMapper pageMapper;
    private final UserMapperImpl userMapperImpl;


    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel> saveStudent(@PathVariable Long id){

        return ResponseEntity.ok().body(ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(userMapper.userDTO(authenticationService.fetchUserById(id)))
                        .build()

                );
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> fetchAllUsers(@RequestParam Map<String,String> param){

        Page<User> studentPages = authenticationService.fetchUsers(param);

        PageDTO pageDTO =pageMapper.pageDTO(studentPages);
        pageDTO.setContent(studentPages.getContent().stream()
                .map(userMapper::userDTO)
                .toList());
        return ResponseEntity.ok().body(
                ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(pageDTO)
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseModel> updateUserById(@PathVariable("id") Long id, @RequestBody RegisterDTO updateDTO){
        return ResponseEntity.ok().body(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(userMapper.userDTO(authenticationService.updateUserById(id,updateDTO)))
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseModel> deleteUserById(@PathVariable Long id){

        authenticationService.deleteUserById(id);
        return ResponseEntity.ok().body(
              ResponseModel.builder()
                      .status(HttpStatus.OK).success(true)
                      .data("User id : " + id + "deleted successfully")
                      .build()
        );
    }
}
