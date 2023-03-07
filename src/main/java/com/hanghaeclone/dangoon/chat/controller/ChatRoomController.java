package com.hanghaeclone.dangoon.chat.controller;

import com.hanghaeclone.dangoon.chat.dto.ChatRoomResponseDto;
import com.hanghaeclone.dangoon.chat.dto.ChatUserResponseDto;
import com.hanghaeclone.dangoon.chat.service.ChatService;
import com.hanghaeclone.dangoon.dto.ResponseDto;
import com.hanghaeclone.dangoon.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatService chatService;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }


//     유저의 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public ResponseDto<List<ChatUserResponseDto>> room(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ChatUserResponseDto> chatUsers = chatService.getRoomListByUser(userDetails.getUser());
        return ResponseDto.success(chatUsers);
    }

//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<ChatUser> room(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return chatService.findAllRoom(userDetails.getUser());
//    }


    // 채팅방 생성
    @PostMapping("/room/{postId}")
    @ResponseBody
    public ResponseDto<String> createRoom(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(chatService.createRoom(postId, userDetails.getUser()));
    }


    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }


    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ResponseDto<ChatRoomResponseDto> roomInfo(@PathVariable String roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(chatService.getRoom(roomId, userDetails.getUser()));
    }
}