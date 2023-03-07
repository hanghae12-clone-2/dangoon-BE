package com.hanghaeclone.dangoon.chat.service;


import com.hanghaeclone.dangoon.chat.entity.ChatRoom;
import com.hanghaeclone.dangoon.chat.entity.ChatUser;
import com.hanghaeclone.dangoon.chat.dto.ChatRoomResponseDto;
import com.hanghaeclone.dangoon.chat.dto.ChatUserResponseDto;
import com.hanghaeclone.dangoon.chat.repository.ChatRoomRepository;
import com.hanghaeclone.dangoon.chat.repository.ChatUserRepository;
import com.hanghaeclone.dangoon.entity.Post;
import com.hanghaeclone.dangoon.entity.User;
import com.hanghaeclone.dangoon.repository.PostRepository;
import com.hanghaeclone.dangoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final UserRepository userRepository;

    //    private Map<String, ChatRoom> chatRooms;
    private final ChatRoomRepository chatRoomRepository;
    private final PostRepository postRepository;
    private final ChatUserRepository chatUserRepository;
//    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
//    private void init() {
//        chatRooms = new LinkedHashMap<>();
//    }

    //채팅방 불러오기
//    public List<ChatUserResponseDto> findAllRoom(User user) {
//        List<ChatUser> result = chatUserRepository.findAllByUser(user);
//        List<ChatUserResponseDto> dtoList = new ArrayList<>();
//        for (ChatUser chatUser : result) {
//            ChatUserResponseDto.of(chatUser);
//            dtoList.add(ChatUserResponseDto.of(chatUser));
//        }
//
//        return dtoList;
//    }
    public List<ChatUserResponseDto> getRoomListByUser(User user) {
        List<ChatUser> chatUsers = chatUserRepository.findAllByUser(user);
        List<ChatUserResponseDto> dtoList = new ArrayList<>();

        for (ChatUser chatUser : chatUsers) {
            dtoList.add(ChatUserResponseDto.of(chatUser));
        }
        return dtoList;
    }

    //채팅방 하나 불러오기
//    public ChatRoom findById(String roomId) {
//        return chatRooms.get(roomId);
//    }

    //채팅방 생성
    public String createRoom(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow( () -> new NullPointerException("게시글이 존재하지 않습니다."));

        //user가 post의 userid와 같으면 return 뭘하지
        if (user.getId().equals(post.getUser().getId())){
            throw new IllegalArgumentException("자신에게 채팅할 수 없습니다.");
        }


        Optional<ChatUser> chatUser = chatUserRepository.findByPostAndUser(post, user);
        if (chatUser.isPresent()){
            return chatUser.get().getChatRoom().getRoomId().toString();
        }

        //없으면 채팅방 새로 만들기


        //게시글 제목으로 채팅방 생성
        ChatRoom chatRoom = ChatRoom.create(post);

        chatRoomRepository.save(chatRoom);

        //구매자와 판매자 ChatUser 테이블에 저장
        ChatUser seller = ChatUser.create(post.getUser(), post, chatRoom);
        ChatUser buyer = ChatUser.create(user, post, chatRoom);
        List<ChatUser> chatUsers = new ArrayList<>();
        chatUsers.add(seller);
        chatUsers.add(buyer);
        chatUserRepository.saveAll(chatUsers);


        return chatRoom.getRoomId();
    }

    @Transactional
    public ChatRoomResponseDto getRoom(String roomId, User user) {

        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow( () -> new NullPointerException("채팅방이 존재하지 않습니다."));
        ChatUser chatUser = chatUserRepository.findByChatRoomAndUser(chatRoom, user);
        chatUser.initUnreadCount();
        return ChatRoomResponseDto.of(chatRoom);

    }



}