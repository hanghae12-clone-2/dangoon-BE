package com.hanghaeclone.dangoon.controller;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.hanghaeclone.dangoon.dto.LoginRequestDto;
import com.hanghaeclone.dangoon.dto.ResponseDto;
import com.hanghaeclone.dangoon.dto.SignupRequestDto;
import com.hanghaeclone.dangoon.dto.UserResponseDto;
import com.hanghaeclone.dangoon.security.UserDetailsImpl;
import com.hanghaeclone.dangoon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users/signup")
    public void signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
    }

    @PostMapping("/users/login")
    public void login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        userService.login(loginRequestDto, response);
    }

    @GetMapping("/users/mypage")
    public ResponseDto<UserResponseDto> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseDto.success(userService.getUser(userDetails));
    }

}
