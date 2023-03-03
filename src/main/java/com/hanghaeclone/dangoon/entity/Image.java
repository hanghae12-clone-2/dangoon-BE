package com.hanghaeclone.dangoon.entity;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @Column(nullable = false)
    private String imageUrl;
}
