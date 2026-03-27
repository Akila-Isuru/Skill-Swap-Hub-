package org.example.backend.service;

import org.example.backend.dto.ChatMessageDTO;
import java.util.List;

public interface ChatService {

    void saveMessage(ChatMessageDTO chatMessageDTO);
    List<ChatMessageDTO> getChatHistory(Long user1, Long user2);
}