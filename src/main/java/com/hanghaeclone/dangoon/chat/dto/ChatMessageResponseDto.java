package com.hanghaeclone.dangoon.chat.dto;

import com.hanghaeclone.dangoon.chat.entity.ChatMessageStorage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageResponseDto {
    private String sender;
    private String message;

    private String createdAt;

    private String image;

    public static ChatMessageResponseDto of(ChatMessageStorage storage) {
        return ChatMessageResponseDto.builder()
                .sender(storage.getSender())
                .message(storage.getMessage())
                .createdAt(storage.getCreatedAt().toString())
                .image(storage.getImage())
                .build();
    }
}
