package com.hanghaeclone.dangoon.service;

import com.hanghaeclone.dangoon.dto.PostRequestDto;
import com.hanghaeclone.dangoon.dto.PostResponseDto;
import com.hanghaeclone.dangoon.dto.ResponseDto;
import com.hanghaeclone.dangoon.entity.Image;
import com.hanghaeclone.dangoon.entity.Post;
import com.hanghaeclone.dangoon.entity.User;
import com.hanghaeclone.dangoon.entity.Wish;
import com.hanghaeclone.dangoon.repository.ImageRepository;
import com.hanghaeclone.dangoon.repository.PostRepository;
import com.hanghaeclone.dangoon.repository.UserRepository;
import com.hanghaeclone.dangoon.repository.WishRepository;
import com.hanghaeclone.dangoon.security.UserDetailsImpl;
import com.hanghaeclone.dangoon.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final WishRepository wishRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, List<MultipartFile> multipartFiles, User user) {

        try {

            Post post = new Post(postRequestDto, user);

            for (MultipartFile file : multipartFiles) {
                String imageUrl = s3Uploader.upload(file);
                Image image = new Image(post, imageUrl);
                imageRepository.save(image);
                post.addImage(image);
            }
            postRepository.save(post);

            return PostResponseDto.of(post);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PostResponseDto getPost(Long id, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("게시글 없음"));
        PostResponseDto dto = PostResponseDto.of(post);
        if(userDetails != null) { //로그인 했을때 관심상품 여부 set
            if(wishRepository.findByUserAndPost(userDetails.getUser(), post).isPresent()) {
                dto.wish();
            }
        }

        return dto;
    }

    public List<PostResponseDto> getPostList(int page, int size, String sortBy, String location, UserDetailsImpl userDetails) {
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
            PostResponseDto dto = PostResponseDto.of(post);
            if(userDetails != null) { //로그인 했을때 관심상품 여부 set
                if(wishRepository.findByUserAndPost(userDetails.getUser(), post).isPresent()) {
                    dto.wish();
                }
            }
            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<PostResponseDto> searchPosts(int page, int size, String sortBy, String keyword, UserDetailsImpl userDetails) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage;
        String query = "%" + keyword.replaceAll(" ", "") + "%";
//        postPage = postRepository.findAllByTitleContainingOrLocationContaining(query, query, pageable);
        postPage = postRepository.findAllByTitleLikeOrLocationLike(query, query, pageable);
        List<PostResponseDto> dtoList = new ArrayList<>();

        for (Post post : postPage) {
            PostResponseDto dto = PostResponseDto.of(post);
            if(userDetails != null) { //로그인 했을때 관심상품 여부 set
                if(wishRepository.findByUserAndPost(userDetails.getUser(), post).isPresent()) {
                    dto.wish();
                }
            }
            dtoList.add(dto);
        }

        return dtoList;

    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글 없음"));
        if(user.getUsername().equals(post.getUser().getUsername())) {
            post.update(requestDto);
            return PostResponseDto.of(post);
        }else {
            throw new IllegalArgumentException("유저 불일치");
        }

    }

    @Transactional
    public String deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글 없음"));
        if(user.getUsername().equals(post.getUser().getUsername())) {
            postRepository.deleteById(postId);
            return "삭제 완료";
        }else {
            throw new IllegalArgumentException("유저 불일치");
        }
    }

    @Transactional
    public String addWish(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글입니다.")
        );

        Optional<Wish> wish = wishRepository.findByUserAndPost(user, post);
        if (wish.isPresent()){
            wishRepository.delete(wish.get());
            post.subWishCount();
            return ("관심 상품 취소");
        }

        wishRepository.save(new Wish(post, user));
        post.addWishCount();
        return ("관심 상품 등록");



    }
}
