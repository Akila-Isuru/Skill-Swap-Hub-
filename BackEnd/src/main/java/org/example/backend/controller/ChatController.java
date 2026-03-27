package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.entity.ChatMessage;
import org.example.backend.repository.ChatMessageRepository;
import org.example.backend.service.ChatService;
import org.example.backend.utill.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@CrossOrigin
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatService chatService;


    @GetMapping("/history/{user1}/{user2}")
    public ResponseEntity<APIResponse> getChatHistory(@PathVariable Long user1, @PathVariable Long user2) {
        return ResponseEntity.ok(new APIResponse(
                200,
                "Success",
                chatService.getChatHistory(user1, user2)
        ));
    }
}