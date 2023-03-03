package com.hanghaeclone.dangoon.service;

import com.hanghaeclone.dangoon.dto.PostRequestDto;
import com.hanghaeclone.dangoon.dto.PostResponseDto;
import com.hanghaeclone.dangoon.dto.ResponseDto;
import com.hanghaeclone.dangoon.entity.Post;
import com.hanghaeclone.dangoon.entity.User;
import com.hanghaeclone.dangoon.repository.PostRepository;
import com.hanghaeclone.dangoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public ResponseDto<PostResponseDto> getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("게시글 없음"));
        return ResponseDto.success(PostResponseDto.of(post));
    }

    public ResponseDto<List<PostResponseDto>> getPostList(int page, int size, String sortBy, String location) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage;

        if(location.equals("all")) {
            postPage = postRepository.findAll(pageable);
        }else {
            postPage = postRepository.findAllByLocation(location, pageable);
        }

        List<PostResponseDto> dtoList = new ArrayList<>();

        for (Post post : postPage) {
            dtoList.add(PostResponseDto.of(post));
        }

        return ResponseDto.success(dtoList);
    }

    public ResponseDto<List<PostResponseDto>> searchPosts(int page, int size, String sortBy, String keyword) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage;
        String query = keyword.replaceAll(" ", "");
        postPage = postRepository.findAllByTitleContainingOrLocationContaining(query, query, pageable);

        List<PostResponseDto> dtoList = new ArrayList<>();

        for (Post post : postPage) {
            dtoList.add(PostResponseDto.of(post));
        }

        return ResponseDto.success(dtoList);

    }

    @Transactional
    public ResponseDto<PostResponseDto> updatePost(Long postId, PostRequestDto requestDto, User user) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글 없음"));
        if(user.getUsername().equals(post.getUser().getUsername())) {
            post.update(requestDto);
            return ResponseDto.success(PostResponseDto.of(post));
        }else {
            throw new IllegalArgumentException("유저 불일치");
        }

    }

    public ResponseDto<String> deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글 없음"));
        if(user.getUsername().equals(post.getUser().getUsername())) {
            postRepository.deleteById(postId);
            return ResponseDto.success("삭제 완료");
        }else {
            throw new IllegalArgumentException("유저 불일치");
        }
    }
}
