package com.hanghaeclone.dangoon.chat.dto;

import com.hanghaeclone.dangoon.chat.entity.ChatUser;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatUserResponseDto {

    private String roomId;
    private String roomName;
    private String imageUrl;
    private String lastChat;
    private String location;

    public static ChatUserResponseDto of(ChatUser chatUser) {
        return ChatUserResponseDto.builder()
                .roomId(chatUser.getChatRoom().getRoomId())
                .roomName(chatUser.getChatRoom().getRoomName())
                .imageUrl(chatUser.getChatRoom().getPost().getImages().get(0).getImageUrl()) // 첫번째 이미지 url
                .lastChat(chatUser.getChatRoom().getChatMessageList().size() > 0 ? chatUser.getChatRoom().getChatMessageList().get(chatUser.getChatRoom().getChatMessageList().size()-1).getMessage() : "채팅 기록이 없습니다.")
                .location(chatUser.getChatRoom().getPost().getLocation())
                .build();
    }

}
