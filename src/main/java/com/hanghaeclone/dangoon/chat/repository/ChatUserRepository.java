package com.hanghaeclone.dangoon.chat.repository;

import com.hanghaeclone.dangoon.chat.entity.ChatRoom;
import com.hanghaeclone.dangoon.chat.entity.ChatUser;
import com.hanghaeclone.dangoon.entity.Post;
import com.hanghaeclone.dangoon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {

    List<ChatUser> findAllByUser(User user);
    List<ChatUser> findByChatRoom(ChatRoom chatRoom);
    Optional<ChatUser> findByPostAndUser(Post post, User user);

    List<ChatUser> findAllByChatRoomAndPost(ChatRoom chatRoom, Post post);

    ChatUser findByChatRoomAndUser(ChatRoom chatRoom, User user);

    ChatUser findByChatRoomAndUserNickNameIsNotContaining(ChatRoom chatRoom, String nickname);
}
