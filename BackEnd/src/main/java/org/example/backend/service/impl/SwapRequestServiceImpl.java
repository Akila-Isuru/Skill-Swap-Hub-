package org.example.backend.service.impl;

import org.example.backend.dto.SwapRequestDTO;
import org.example.backend.entity.enums.RequestStatus;
import org.example.backend.entity.SwapRequest;
import org.example.backend.entity.User;
import org.example.backend.exception.CustomException;
import org.example.backend.repository.SwapRequestRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.NotificationService;
import org.example.backend.service.SwapRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SwapRequestServiceImpl implements SwapRequestService {

    @Autowired
    private SwapRequestRepository swapRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private NotificationService notificationService;

    @Override
    public void sendRequest(SwapRequestDTO dto) {
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new CustomException("Sender not found"));
        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new CustomException("Receiver not found"));

        SwapRequest request = new SwapRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setMessage(dto.getMessage());
        request.setStatus(RequestStatus.PENDING);

        swapRequestRepository.save(request);

        // Receiver ta notification dennawa
        notificationService.createNotification(
                receiver.getId(),
                sender.getName() + " sent you a swap request!",
                "SWAP"
        );
    }

    @Override
    public void updateStatus(Long requestId, String status) {
        SwapRequest request = swapRequestRepository.findById(requestId)
                .orElseThrow(() -> new CustomException("Request not found"));

        RequestStatus newStatus = RequestStatus.valueOf(status);
        request.setStatus(newStatus);
        swapRequestRepository.save(request);

        // Status change notification - sender ta kiyannawa
        String notifMsg;
        if (newStatus == RequestStatus.ACCEPTED) {
            notifMsg = request.getReceiver().getName() + " accepted your swap request!";
        } else {
            notifMsg = request.getReceiver().getName() + " declined your swap request.";
        }
        notificationService.createNotification(request.getSender().getId(), notifMsg, "SWAP");
    }

    @Override
    public List<SwapRequestDTO> getIncomingRequests(Long userId) {
        List<SwapRequest> requests = swapRequestRepository.findAll().stream()
                .filter(r -> r.getReceiver().getId().equals(userId))
                .collect(Collectors.toList());

        return requests.stream()
                .map(req -> mapToDTO(req))
                .collect(Collectors.toList());
    }

    @Override
    public List<SwapRequestDTO> getOutgoingRequests(Long userId) {
        List<SwapRequest> requests = swapRequestRepository.findAll().stream()
                .filter(r -> r.getSender().getId().equals(userId))
                .collect(Collectors.toList());

        return requests.stream()
                .map(req -> mapToDTO(req))
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------
    // Helper: SwapRequest -> SwapRequestDTO properly map karannawa
    // senderName, receiverName, skillName set karanawa
    // -------------------------------------------------------
    private SwapRequestDTO mapToDTO(SwapRequest req) {
        SwapRequestDTO dto = new SwapRequestDTO();
        dto.setId(req.getId());

        // Sender info
        dto.setSenderId(req.getSender().getId());
        dto.setSenderName(req.getSender().getName());   // FIX: "User 1" wenuwata real name

        // Receiver info
        dto.setReceiverId(req.getReceiver().getId());
        dto.setReceiverName(req.getReceiver().getName());

        dto.setMessage(req.getMessage());
        dto.setStatus(req.getStatus().name());

        // FIX: skillName - message eken extract karanawa
        // message format: "I want to swap skills with you for <SkillName>"
        // Entity eke skillId field naha, so message parse karanna
        dto.setSkillName(extractSkillNameFromMessage(req.getMessage()));

        return dto;
    }

    // "I want to swap skills with you for Photography Basics" -> "Photography Basics"
    private String extractSkillNameFromMessage(String message) {
        if (message == null) return "Unknown Skill";
        String prefix = "I want to swap skills with you for ";
        if (message.startsWith(prefix)) {
            return message.substring(prefix.length()).trim();
        }
        return message; // message format different nam whole message return
    }
}