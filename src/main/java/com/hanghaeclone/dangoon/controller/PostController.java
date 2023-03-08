package com.hanghaeclone.dangoon.controller;

import com.hanghaeclone.dangoon.dto.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    @GetMapping("/posts/{postId}")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.getPost(postId, userDetails));
    }

    @GetMapping("/posts/my")
    public ResponseDto<List<PostListResponseDto>> getPostListByUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.getPostListByUser(userDetails.getUser()));
    }

    @GetMapping("/posts")
    public ResponseDto<List<PostListResponseDto>> getPostList(@RequestParam int page,
                                                              @RequestParam int size,
                                                              @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                              @RequestParam(required = false, defaultValue = "all") String location,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseDto.success(postService.getPostList(page-1, size, sortBy, location, userDetails));
    }

    @PutMapping("/posts/{postId}")
    public ResponseDto<PostResponseDto> updatePost(@PathVariable Long postId,
                                                   @RequestPart PostUpdateRequestDto postUpdateRequestDto,
                                                   @RequestPart(required = false) List<MultipartFile> multipartFiles,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.updatePost(postId, postUpdateRequestDto, multipartFiles, userDetails.getUser()));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseDto<String> updatePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.deletePost(postId, userDetails.getUser()));
    }

    @GetMapping("/search/posts")
    public ResponseDto<List<PostListResponseDto>> searchPosts(@RequestParam int page,
                                                          @RequestParam int size,
                                                          @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                          @RequestParam(required = false) String keyword,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {


        return ResponseDto.success(postService.searchPosts(page-1, size, sortBy, keyword, userDetails));
    }


    @PostMapping("/posts")
    public ResponseDto<PostResponseDto> createPost(@RequestPart PostRequestDto postRequestDto,
                                                   @RequestPart List<MultipartFile> multipartFiles,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(postService.createPost(postRequestDto, multipartFiles, userDetails.getUser()));
    }

    @PostMapping("/wishes/{postId}")
    public ResponseDto<String> addWish(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseDto.success(postService.addWish(postId, userDetails.getUser()));
    }

}
