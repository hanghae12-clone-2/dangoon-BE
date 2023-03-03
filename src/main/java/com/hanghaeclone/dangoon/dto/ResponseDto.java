package com.hanghaeclone.dangoon.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public static <T> ResponseDto<T> success(T result) {
        return new ResponseDto<>(200, result);
    }

    public static <T> ResponseDto<T> fail() {
        return new ResponseDto<>(400, null);
    }

    @Getter
    @AllArgsConstructor
    public static class Error {
        private String errorMessage;
    }
}
