package com.hanghaeclone.dangoon.controller;

import com.hanghaeclone.dangoon.dto.ResponseDto;
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

//    @PostMapping("/like/{postId}")
//    public ResponseDto<String> likeUser(@PathVariable Long postId, @RequestBody String type, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return ResponseDto.success(temperatureService.likeUser(postId, type, userDetails.getUser()));
//    }

//    @PostMapping("/hate/{postId}")
//    public ResponseDto<String> hateUser(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return ResponseDto.success(temperatureService.hateUser(postId, userDetails.getUser()));
//    }
}
