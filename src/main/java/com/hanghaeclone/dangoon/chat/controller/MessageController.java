package com.hanghaeclone.dangoon.chat.controller;


import com.hanghaeclone.dangoon.chat.model.ChatMessage;
import com.hanghaeclone.dangoon.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/message")
    public void enter(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
//            message.setMessage(message.getSender()+"님이 입장하였습니다.");
            message.setMessage(LocalDateTime.now().toString());
        }else {
            chatMessageService.createChat(message);
        }
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);
    }
}