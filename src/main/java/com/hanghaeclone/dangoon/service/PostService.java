package com.hanghaeclone.dangoon.service;


import com.hanghaeclone.dangoon.dto.PostRequestDto;
import com.hanghaeclone.dangoon.dto.PostResponseDto;
import com.hanghaeclone.dangoon.entity.Post;
import com.hanghaeclone.dangoon.entity.User;
import com.hanghaeclone.dangoon.repository.PostRepository;
import com.hanghaeclone.dangoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {

        Post post = new Post(postRequestDto, user);

        postRepository.save(post);

        return new PostResponseDto(post);
    }
}
