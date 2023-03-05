package com.hanghaeclone.dangoon.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public static <T> ResponseDto<T> success(T result) {
        return new ResponseDto<>(200, result);
    }

    public static <T> ResponseDto<T> fail(HttpStatus httpStatus, T result) {
        return new ResponseDto<>(httpStatus.value(), result);
    }

    @Getter
    @AllArgsConstructor
    public static class Error {
        private String errorMessage;
    }
}
