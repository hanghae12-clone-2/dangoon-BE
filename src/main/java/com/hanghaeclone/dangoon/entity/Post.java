package com.hanghaeclone.dangoon.entity;

import com.hanghaeclone.dangoon.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int chatCount;

    @Column(nullable = false)
    private int wishCount;

    @Column(nullable = false)
    private String location;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, User user) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.price = postRequestDto.getPrice();
        this.wishCount = 0;
        this.location = postRequestDto.getLocation();
        this.user = user;
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.price = requestDto.getPrice();
    }

    public void addChatCount() {
        this.chatCount++;
    }

    public void subWishCount(){
        this.wishCount--;
    }

    public void addWishCount(){
        this.wishCount++;
    }

    public void addImage(Image image) {
        images.add(image);
    }
}
