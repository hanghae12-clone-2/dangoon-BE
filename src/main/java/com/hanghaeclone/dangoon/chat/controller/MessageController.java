package com.hanghaeclone.dangoon.chat.controller;


import com.hanghaeclone.dangoon.chat.dto.ChatMessageRequestDto;
import com.hanghaeclone.dangoon.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/message")
    public void enter(ChatMessageRequestDto message) {
        if (ChatMessageRequestDto.MessageType.ENTER.equals(message.getType())) {
//            message.setMessage(message.getSender()+"님이 입장하였습니다.");
            message.setMessage(LocalDateTime.now().toString());
        }else {
            chatMessageService.createChat(message);
        }
        message.setCreatedAt(LocalDateTime.now().toString());
        log.info("image -> {}", message.getImage());
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);
    }
}