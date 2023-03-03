package com.hanghaeclone.dangoon.dto;

import com.hanghaeclone.dangoon.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private int price;
    private String nickname;
    private int wishCount;
    private String location;

    private String createdAt;

    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .price(post.getPrice())
                .nickname(post.getUser().getNickName())
                .wishCount(post.getWishCount())
                .location(post.getLocation())
                .createdAt(post.getCreatedAt().toString())
                .build();
    }

    public PostResponseDto(Post post) {
        this.postid = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.nickName = post.getUser().getNickName();
        this.wishCount = post.getWishCount();
        this.location = post.getLocation();
        this.createdAt = post.getCreatedAt();
    }

}
