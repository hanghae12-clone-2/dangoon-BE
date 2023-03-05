package com.hanghaeclone.dangoon.chat.service;

import com.hanghaeclone.dangoon.chat.dto.ChatMessageRequestDto;
import com.hanghaeclone.dangoon.chat.entity.ChatMessageStorage;
import com.hanghaeclone.dangoon.chat.entity.ChatRoom;
import com.hanghaeclone.dangoon.chat.repository.ChatMessageRepository;
import com.hanghaeclone.dangoon.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    public void createChat(ChatMessageRequestDto message) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId()).orElseThrow( () -> new NullPointerException("채팅방이 존재하지 않습니다."));
        ChatMessageStorage chatMessageStorage = ChatMessageStorage.of(message);
        chatMessageStorage.setChatRoom(chatRoom);
        chatMessageRepository.save(chatMessageStorage);

    }
}
