package com.hanghaeclone.dangoon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String email;
    private String nicknmae;

    public KakaoUserInfoDto(Long id, String nicknmae, String email) {
        this.id = id;
        this.nicknmae = nicknmae;
        this.email = email;
    }
}