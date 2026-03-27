package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.ChatMessageDTO;
import org.example.backend.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;


    @MessageMapping("/chat.sendMessage")
    public void processMessage(@Payload ChatMessageDTO chatMessageDTO) {


        chatService.saveMessage(chatMessageDTO);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessageDTO.getReceiverId()),
                "/queue/messages",
                chatMessageDTO
        );
    }
}