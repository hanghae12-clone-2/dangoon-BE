package com.hanghaeclone.dangoon.controller;

import com.hanghaeclone.dangoon.dto.PostRequestDto;
import com.hanghaeclone.dangoon.dto.PostResponseDto;
import com.hanghaeclone.dangoon.dto.ResponseDto;
import com.hanghaeclone.dangoon.security.UserDetailsImpl;
import com.hanghaeclone.dangoon.service.PostService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    @GetMapping("/posts/{postId}")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long postId) {
        return ResponseDto.success(postService.getPost(postId));
    }

    @GetMapping("/posts")
    public ResponseDto<List<PostResponseDto>> getPostList(@RequestParam int page,
                                                          @RequestParam int size,
                                                          @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                          @RequestParam(required = false, defaultValue = "all") String location) {

        return ResponseDto.success(postService.getPostList(page-1, size, sortBy, location));
    }

    @PutMapping("/posts/{postId}")
    public ResponseDto<PostResponseDto> updatePost(@PathVariable Long postId, PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.updatePost(postId, requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseDto<String> updatePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.deletePost(postId, userDetails.getUser()));
    }

    @GetMapping("/search/posts")
    public ResponseDto<List<PostResponseDto>> searchPosts(@RequestParam int page,
                                                          @RequestParam int size,
                                                          @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                          @RequestParam(required = false) String keyword) {

        return ResponseDto.success(postService.searchPosts(page-1, size, sortBy, keyword));
    }


    @PostMapping("/posts")
    public ResponseDto<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.createPost(postRequestDto, userDetails.getUser()));
    }

    @PostMapping("/wishes/{postId}")
    public ResponseDto<String> addWish(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseDto.success(postService.addWish(postId, userDetails.getUser()));
    }

}
