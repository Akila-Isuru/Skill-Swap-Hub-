package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.ChatMessageDTO;
import org.example.backend.entity.ChatMessage;
import org.example.backend.entity.User;
import org.example.backend.repository.ChatMessageRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.ChatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    @Override
    public void saveMessage(ChatMessageDTO dto) {
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .content(dto.getContent())
                .build();

        chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessageDTO> getChatHistory(Long user1, Long user2) {
        return chatMessageRepository.findChatHistory(user1, user2)
                .stream()
                .map(msg -> new ChatMessageDTO(
                        msg.getId(),
                        msg.getSender().getId(),
                        msg.getReceiver().getId(),
                        msg.getContent(),
                        msg.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}