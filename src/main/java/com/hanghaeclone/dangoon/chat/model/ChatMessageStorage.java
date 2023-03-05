package com.hanghaeclone.dangoon.chat.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ChatMessage.MessageType type;
    //채팅방 ID
    //보내는 사람
    private String sender;
    //내용
    private String message;

    @ManyToOne
    private ChatRoom chatRoom;

    public static ChatMessageStorage of(ChatMessage message) {
        return ChatMessageStorage.builder()
                .type(message.getType())
                .sender(message.getSender())
                .message(message.getMessage())
                .build();
    }
}
