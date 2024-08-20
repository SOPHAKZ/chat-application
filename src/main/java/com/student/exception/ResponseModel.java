package com.student.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel {
    private HttpStatus status;
    private boolean success;
    private Object data;
    private Object errors;
}
