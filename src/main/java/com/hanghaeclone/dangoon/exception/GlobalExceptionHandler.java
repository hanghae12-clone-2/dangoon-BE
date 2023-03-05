package com.hanghaeclone.dangoon.exception;

import com.hanghaeclone.dangoon.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<?> handle(Exception ex) {
        ex.printStackTrace();
        return ResponseDto.fail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto<?> handleException(Exception ex) {
        return ResponseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
