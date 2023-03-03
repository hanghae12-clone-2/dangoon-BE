package com.hanghaeclone.dangoon.dto;

import com.hanghaeclone.dangoon.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long postid;
    private String title;
    private String content;
    private int price;
    private String nickName;
    private int wishCount;
    private String location;
    private LocalDateTime createdAt;

    public PostResponseDto(Long postid, String title, String content, int price, String nickName,
                           int wishCount, String location, LocalDateTime createdAt) {
        this.postid = postid;
        this.title = title;
        this.content = content;
        this.price = price;
        this.nickName = nickName;
        this.wishCount = wishCount;
        this.location = location;
        this.createdAt = createdAt;
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
