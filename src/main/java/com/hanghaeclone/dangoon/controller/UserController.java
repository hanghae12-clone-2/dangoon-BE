package com.hanghaeclone.dangoon.controller;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghaeclone.dangoon.dto.LoginRequestDto;
import com.hanghaeclone.dangoon.dto.ResponseDto;
import com.hanghaeclone.dangoon.dto.SignupRequestDto;
import com.hanghaeclone.dangoon.dto.UserResponseDto;
import com.hanghaeclone.dangoon.jwt.JwtUtil;
import com.hanghaeclone.dangoon.security.UserDetailsImpl;
import com.hanghaeclone.dangoon.service.KakaoService;
import com.hanghaeclone.dangoon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    @PostMapping("/users/signup")
    public ResponseDto<String> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return ResponseDto.success(userService.signup(signupRequestDto));
    }

    @PostMapping("/users/login")
    public ResponseDto<String> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return ResponseDto.success(userService.login(loginRequestDto, response));
    }


    @GetMapping("/users/mypage")
    public ResponseDto<UserResponseDto> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseDto.success(userService.getUser(userDetails));
    }

    @GetMapping("/users/kakao/callback")
    public ResponseDto<String> kakaoCallback(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code : 카카오 서버로부터 받은 인가 코드
        String createToken = kakaoService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 Set
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken);
        cookie.setPath("/");
        response.addCookie(cookie);
        try {
            response.sendRedirect("http://hanghae77.s3-website.ap-northeast-2.amazonaws.com/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseDto.success("로그인 완료");
    }

    @Value("${KAKAO_API_KEY}")
    private String kakaoApiKey;

    @GetMapping("/kakao-key")
    public ResponseDto<String> getApiKey() {
        return ResponseDto.success(kakaoApiKey);
    }

}
