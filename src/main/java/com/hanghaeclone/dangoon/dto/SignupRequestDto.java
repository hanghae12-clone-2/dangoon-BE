package com.hanghaeclone.dangoon.dto;

import lombok.Getter;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
public class SignupRequestDto {

    @Size(min= 4, max = 10)
    private String username;

    @Pattern(regexp = "(?=.*?[a-zA-Z])(?=.*?[\\d])(?=.*?[~!@#$%^&*()_+=\\-`]).{8,15}")
    private String password;

    private String nickName;

    private float temperature;

}
