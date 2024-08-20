package com.student.exception;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ResponseModel> handleValidationException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .body(ResponseModel.builder()
                        .status(BAD_REQUEST)
                        .success(false)
                        .errors(errors)
                        .build()
                );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ResponseModel> handleRuntimeException(RuntimeException ex){

        return ResponseEntity
                .badRequest()
                .body(ResponseModel.builder()
                        .status(BAD_REQUEST)
                        .success(false)
                        .errors(ex.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ResponseModel> handleIllegalArgumentException(IllegalArgumentException ex){

        return ResponseEntity
                .badRequest()
                .body(ResponseModel.builder()
                        .status(BAD_REQUEST)
                        .success(false)
                        .errors(ex.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ResponseModel> handleResourceNotFoundException(EntityNotFoundException ex){

        return ResponseEntity
                .status(NOT_FOUND)
                .body(ResponseModel.builder()
                        .status(NOT_FOUND)
                        .success(false)
                        .errors(ex.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ResponseModel> handleBadCredentialsException(BadCredentialsException ex){

        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ResponseModel.builder()
                        .status(UNAUTHORIZED)
                        .success(false)
                        .errors(ex.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ResponseModel> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex){

        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ResponseModel.builder()
                        .status(UNAUTHORIZED)
                        .success(false)
                        .errors(ex.getMessage())
                        .build()
                );
    }





}
