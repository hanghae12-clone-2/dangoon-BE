package com.hanghaeclone.dangoon.dto;

import com.hanghaeclone.dangoon.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Default;

import java.util.List;

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
    private double temperature;
    private int wishCount;
    private int chatCount;
    private Boolean isWish;
    private String location;

    private String createdAt;
    private List<String> imageUrlList;

    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .postid(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .price(post.getPrice())
                .nickname(post.getUser().getNickName())
                .temperature(post.getUser().getTemperature())
                .isWish(false)
                .wishCount(post.getWishCount())
                .chatCount(post.getChatCount())
                .location(post.getLocation())
                .createdAt(post.getCreatedAt().toString())
                .imageUrlList(post.getImages().stream().map(image -> image.getImageUrl()).toList())
                .build();
    }

    public void wish() {
        this.isWish = true;
    }


}
