package com.hanghaeclone.dangoon.chat.model.dto;

import com.hanghaeclone.dangoon.chat.model.ChatMessageStorage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageResponseDto {
    private String sender;
    private String message;

    public static ChatMessageResponseDto of(ChatMessageStorage storage) {
        return ChatMessageResponseDto.builder()
                .sender(storage.getSender())
                .message(storage.getMessage())
                .build();
    }
}
