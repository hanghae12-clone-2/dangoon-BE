package com.hanghaeclone.dangoon.dto;

import com.hanghaeclone.dangoon.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Default;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Long postid;
    private String title;
    private String content;
    private int price;
    private String nickname;
    private int wishCount;
    private Boolean isWish;
    private String location;

    private String createdAt;

    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .postid(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .price(post.getPrice())
                .nickname(post.getUser().getNickName())
                .isWish(false)
                .wishCount(post.getWishCount())
                .location(post.getLocation())
                .createdAt(post.getCreatedAt().toString())
                .build();
    }

    public void wish() {
        this.isWish = true;
    }


}
