package com.hanghaeclone.dangoon.controller;

import com.hanghaeclone.dangoon.dto.PostRequestDto;
import com.hanghaeclone.dangoon.dto.PostResponseDto;
import com.hanghaeclone.dangoon.dto.ResponseDto;
import com.hanghaeclone.dangoon.security.UserDetailsImpl;
import com.hanghaeclone.dangoon.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.List.of;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseDto<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.createPost(postRequestDto, userDetails.getUser()));
    }

}
