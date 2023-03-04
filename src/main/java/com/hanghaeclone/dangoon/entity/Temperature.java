package com.hanghaeclone.dangoon.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Temperature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;


    @Column
    private String status;

    public Temperature(Post post, User user, String status) {
        this.post = post;
        this.user = user;
        this.status = status;
    }

    public void updateLike() {
        this.status = "like";
    }

    public void updateHate() {
        this.status = "hate";
    }
}
