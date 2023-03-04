package com.hanghaeclone.dangoon.dto;


import com.hanghaeclone.dangoon.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String username;
    private String nickName;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.nickName = user.getNickName();
    }

    public String getUsername() {
        return username;
    }

    public String getNickName(){
        return nickName;
    }
}