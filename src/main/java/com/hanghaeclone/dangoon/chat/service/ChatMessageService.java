package com.hanghaeclone.dangoon.chat.service;

import com.hanghaeclone.dangoon.chat.dto.ChatMessageRequestDto;
import com.hanghaeclone.dangoon.chat.entity.ChatMessageStorage;
import com.hanghaeclone.dangoon.chat.entity.ChatRoom;
import com.hanghaeclone.dangoon.chat.entity.ChatUser;
import com.hanghaeclone.dangoon.chat.repository.ChatMessageRepository;
import com.hanghaeclone.dangoon.chat.repository.ChatRoomRepository;
import com.hanghaeclone.dangoon.chat.repository.ChatUserRepository;
import com.hanghaeclone.dangoon.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatUserRepository chatUserRepository;

    @Transactional
    public void createChat(ChatMessageRequestDto message) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId()).orElseThrow( () -> new NullPointerException("채팅방이 존재하지 않습니다."));
        Post post = chatRoom.getPost();

//        ChatUser chatUser = chatUserRepository.findByPostAndUser(post, post.getUser()).orElseThrow(() -> new NullPointerException("채팅중인 유저가 존재하지 않습니다"));
        List<ChatUser> chatUsers = chatUserRepository.findAllByChatRoomAndPost(chatRoom, post);
        for (ChatUser chatUser : chatUsers) {
            if(!chatUser.getUser().getNickName().equals(message.getSender())) {
                chatUser.addUnreadMessageCount();
            }
        }

        ChatMessageStorage chatMessageStorage = ChatMessageStorage.of(message);
        chatMessageStorage.setChatRoom(chatRoom);
        chatMessageRepository.save(chatMessageStorage);

    }
}
