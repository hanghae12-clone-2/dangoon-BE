package com.hanghaeclone.dangoon.entity;

import javax.persistence.*;

@Entity
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
    private int wishCount;

    @Column(nullable = false)
    private String location;

    @ManyToOne
    private User user;


}
