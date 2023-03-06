package com.hanghaeclone.dangoon.chat.entity;

import com.hanghaeclone.dangoon.chat.dto.ChatMessageRequestDto;
import com.hanghaeclone.dangoon.entity.TimeStamped;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageStorage extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ChatMessageRequestDto.MessageType type;
    //채팅방 ID
    //보내는 사람
    private String sender;
    //내용
    private String message;

    @ManyToOne
    private ChatRoom chatRoom;

    public static ChatMessageStorage of(ChatMessageRequestDto message) {
        return ChatMessageStorage.builder()
                .type(message.getType())
                .sender(message.getSender())
                .message(message.getMessage())
                .build();
    }
}
