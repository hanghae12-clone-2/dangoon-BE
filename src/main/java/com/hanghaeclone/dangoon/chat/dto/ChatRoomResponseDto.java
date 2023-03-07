package com.hanghaeclone.dangoon.chat.dto;

import com.hanghaeclone.dangoon.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatRoomResponseDto {
    private String title;
    private int price;
    private String partner;
    List<ChatMessageResponseDto> messageDtoList;

    public static ChatRoomResponseDto of(ChatRoom chatRoom, String partner) {
        return ChatRoomResponseDto.builder()
                .title(chatRoom.getPost().getTitle())
                .price(chatRoom.getPost().getPrice())
                .messageDtoList(chatRoom.getChatMessageList().stream().map(ChatMessageResponseDto::of).toList())
                .partner(partner)
                .build();
    }
}
