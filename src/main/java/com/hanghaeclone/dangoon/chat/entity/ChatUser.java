package com.hanghaeclone.dangoon.chat.entity;

import com.hanghaeclone.dangoon.entity.Post;
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

    private Integer unreadMessageCount;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    @ManyToOne
    private ChatRoom chatRoom;

    public static ChatUser create(User user, Post post, ChatRoom chatRoom) {
        return ChatUser.builder()
                .user(user)
                .post(post)
                .chatRoom(chatRoom)
                .unreadMessageCount(0)
                .build();
    }

    public void addUnreadMessageCount() {
        this.unreadMessageCount++;
    }

    public void initUnreadCount() {
        this.unreadMessageCount = 0;
    }
}
