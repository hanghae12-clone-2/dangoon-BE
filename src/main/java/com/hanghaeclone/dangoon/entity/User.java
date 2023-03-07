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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private float temperature;

    private Long kakaoId;
    private String email;

    public User(String username, String password, String nickName) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.temperature = 36.5f;
    }

    public User(String nickName, Long kakaoId, String password, String email) {
        this.password = password;
        this.nickName = nickName;
        this.kakaoId = kakaoId;
        this.email = email;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void subTemperature(){
        this.temperature -= 0.4;
    }
    public void cancelSubTempearture(){
        this.temperature += 0.4;
    }

    public void addTempearture(){
        this.temperature += 0.7;
    }
    public void cancelAddTempearture(){
        this.temperature -= 0.7;
    }
}
