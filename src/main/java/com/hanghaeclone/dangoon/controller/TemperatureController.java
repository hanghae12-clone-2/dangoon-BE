package com.hanghaeclone.dangoon.controller;

import com.hanghaeclone.dangoon.dto.ResponseDto;
import com.hanghaeclone.dangoon.dto.TemperatureResponseDto;
import com.hanghaeclone.dangoon.security.UserDetailsImpl;
import com.hanghaeclone.dangoon.service.TemperatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TemperatureController {

    private final TemperatureService temperatureService;

    @PostMapping("/like/{postId}")
    public ResponseDto<TemperatureResponseDto> likeUser(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseDto.success(temperatureService.likeUser(postId, userDetails.getUser()));
    }

    @PostMapping("/hate/{postId}")
    public ResponseDto<TemperatureResponseDto> hateUser(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseDto.success(temperatureService.hateUser(postId, userDetails.getUser()));
    }
}
