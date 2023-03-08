package com.hanghaeclone.dangoon.service;

import com.hanghaeclone.dangoon.dto.PostListResponseDto;
import com.hanghaeclone.dangoon.dto.PostRequestDto;
import com.hanghaeclone.dangoon.dto.PostResponseDto;
import com.hanghaeclone.dangoon.dto.PostUpdateRequestDto;
import com.hanghaeclone.dangoon.entity.Image;
import com.hanghaeclone.dangoon.entity.Post;
import com.hanghaeclone.dangoon.entity.User;
import com.hanghaeclone.dangoon.entity.Wish;
import com.hanghaeclone.dangoon.repository.ImageRepository;
import com.hanghaeclone.dangoon.repository.PostRepository;
import com.hanghaeclone.dangoon.repository.WishRepository;
import com.hanghaeclone.dangoon.security.UserDetailsImpl;
import com.hanghaeclone.dangoon.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
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
            throw new IllegalArgumentException("최소 한 장의 사진을 업로드해야 합니다.");
        }
    }

    public PostResponseDto getPost(Long id, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("게시글 없음"));
        PostResponseDto dto = PostResponseDto.of(post);
        if (userDetails != null) { //로그인 했을때 관심상품 여부 set
            if (wishRepository.findByUserAndPost(userDetails.getUser(), post).isPresent()) {
                dto.wish();
            }
        }

        return dto;
    }

    public List<PostListResponseDto> getPostList(int page, int size, String sortBy, String location, UserDetailsImpl userDetails) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage;

        if (location.equals("all")) {
            postPage = postRepository.findAll(pageable);
        } else {
            postPage = postRepository.findAllByLocation(location, pageable);
        }

        List<PostListResponseDto> dtoList = new ArrayList<>();

        for (Post post : postPage) {
            PostListResponseDto dto = PostListResponseDto.of(post);
            if (userDetails != null) { //로그인 했을때 관심상품 여부 set
                if (wishRepository.findByUserAndPost(userDetails.getUser(), post).isPresent()) {
                    dto.wish();
                }
            }
            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<PostListResponseDto> searchPosts(int page, int size, String sortBy, String keyword, UserDetailsImpl userDetails) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage;
        String query = "%" + keyword.replaceAll(" ", "") + "%";
//        postPage = postRepository.findAllByTitleContainingOrLocationContaining(query, query, pageable);
        postPage = postRepository.findAllByTitleLikeOrLocationLike(query, query, pageable);
        List<PostListResponseDto> dtoList = new ArrayList<>();

        for (Post post : postPage) {
            PostListResponseDto dto = PostListResponseDto.of(post);
            if (userDetails != null) { //로그인 했을때 관심상품 여부 set
                if (wishRepository.findByUserAndPost(userDetails.getUser(), post).isPresent()) {
                    dto.wish();
                }
            }
            dtoList.add(dto);
        }

        return dtoList;

    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostUpdateRequestDto requestDto, List<MultipartFile> multipartFiles, User user) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글 없음"));
        if (user.getUsername().equals(post.getUser().getUsername())) {
            post.update(requestDto);

            List<String> remainingImages = requestDto.getRemainingImagesUrlList();

            if(remainingImages.isEmpty() && (multipartFiles.isEmpty() || multipartFiles.get(0).isEmpty())) {
                throw new IllegalArgumentException("최소 한 장의 사진이 필요합니다.");
            }

            List<Image> images = imageRepository.findImagesByPostId(postId);

            try { // 기존에 업로드한 이미지가 갱신된 판매글에 없을경우 이미지를 S3에서 제거하고 DB에서도 삭제
                for (Image image : images) {
                    if (!remainingImages.contains(image.getImageUrl())) {
                        post.getImages().remove(image);
                        String source = URLDecoder.decode(image.getImageUrl().replace("https://dangoon.s3.ap-northeast-2.amazonaws.com/", ""), "UTF-8");
                        s3Uploader.delete(source);
                        imageRepository.delete(image);
                    }
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            try {
                if (!multipartFiles.isEmpty() && !multipartFiles.get(0).isEmpty()) {
                    for (MultipartFile file : multipartFiles) {
                        String imageUrl = s3Uploader.upload(file);
                        Image image = new Image(post, imageUrl);
                        imageRepository.save(image);
                        post.addImage(image);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return PostResponseDto.of(post);
        } else {
            throw new IllegalArgumentException("유저 불일치");
        }

    }

    @Transactional
    public String deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글 없음"));
        if (user.getUsername().equals(post.getUser().getUsername())) {
            if (post.getImages().size() > 0) {
                try {
                    for (Image image : post.getImages()) {
                        String source = URLDecoder.decode(image.getImageUrl().replace("https://dangoon.s3.ap-northeast-2.amazonaws.com/", ""), "UTF-8");
                        s3Uploader.delete(source);
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }

            postRepository.deleteById(postId);
            return "삭제 완료";
        } else {
            throw new IllegalArgumentException("유저 불일치");
        }
    }

    @Transactional
    public String addWish(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글입니다.")
        );

        Optional<Wish> wish = wishRepository.findByUserAndPost(user, post);
        if (wish.isPresent()) {
            wishRepository.delete(wish.get());
            post.subWishCount();
            return ("관심 상품 취소");
        }

        wishRepository.save(new Wish(post, user));
        post.addWishCount();
        return ("관심 상품 등록");


    }

    public List<PostListResponseDto> getPostListByUser(User user) {
        List<Post> posts = postRepository.findAllByUser(user);
        List<PostListResponseDto> dtoList = new ArrayList<>();
        for (Post post : posts) {
            PostListResponseDto dto = PostListResponseDto.of(post);
            if (wishRepository.findByUserAndPost(user, post).isPresent()) {
                dto.wish();
            }
            dtoList.add(dto);
        }
        return dtoList;
    }
}
