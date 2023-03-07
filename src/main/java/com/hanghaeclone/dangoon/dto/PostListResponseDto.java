package com.hanghaeclone.dangoon.dto;

import com.hanghaeclone.dangoon.entity.Image;
import com.hanghaeclone.dangoon.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostListResponseDto {
    private Long postid;
    private String title;
    private String content;
    private int price;
    private String nickname;
    private int wishCount;
    private Boolean isWish;
    private String location;

    private String createdAt;
    private String thumbnailUrl;

    public static PostListResponseDto of(Post post) {
        return PostListResponseDto.builder()
                .postid(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .price(post.getPrice())
                .nickname(post.getUser().getNickName())
                .isWish(false)
                .wishCount(post.getWishCount())
                .location(post.getLocation())
                .createdAt(post.getCreatedAt().toString())
                .thumbnailUrl(post.getImages().get(0).getImageUrl())
                .build();
    }

    public void wish() {
        this.isWish = true;
    }

}
