package com.hanghaeclone.dangoon.chat.repository;

import com.hanghaeclone.dangoon.chat.model.ChatMessageStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessageStorage, Long> {



}
