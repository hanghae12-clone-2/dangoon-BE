package com.hanghaeclone.dangoon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column(nullable = false)
    private String imageUrl;

    public Image(Post post, String imageUrl) {
        this.post = post;
        this.imageUrl = imageUrl;
    }
}
