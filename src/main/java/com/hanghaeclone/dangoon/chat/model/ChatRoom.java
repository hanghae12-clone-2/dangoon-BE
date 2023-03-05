package com.hanghaeclone.dangoon.chat.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hanghaeclone.dangoon.entity.Post;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomId;
    private String roomName;
    @ManyToOne
    private Post post;

    @OneToMany(mappedBy = "chatRoom",cascade = CascadeType.REMOVE)
    List<ChatMessageStorage> chatMessageList = new ArrayList<>();




    public static ChatRoom create(Post post) {
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(post.getTitle())
                .post(post)
                .build();
    }
}