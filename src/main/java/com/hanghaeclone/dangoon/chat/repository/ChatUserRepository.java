package com.hanghaeclone.dangoon.chat.repository;

import com.hanghaeclone.dangoon.chat.entity.ChatRoom;
import com.hanghaeclone.dangoon.chat.entity.ChatUser;
import com.hanghaeclone.dangoon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {

    List<ChatUser> findAllByUser(User user);
    List<ChatUser> findByChatRoom(ChatRoom chatRoom);

}
