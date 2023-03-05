package com.hanghaeclone.dangoon.chat.entity;

import com.hanghaeclone.dangoon.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private ChatRoom chatRoom;

    public static ChatUser create(User user, ChatRoom chatRoom) {
        return ChatUser.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }
}
