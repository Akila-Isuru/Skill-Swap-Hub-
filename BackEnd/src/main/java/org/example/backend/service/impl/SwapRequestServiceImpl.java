package org.example.backend.service.impl;

import org.example.backend.dto.SwapRequestDTO;
import org.example.backend.entity.enums.RequestStatus;
import org.example.backend.entity.SwapRequest;
import org.example.backend.entity.User;
import org.example.backend.exception.CustomException;
import org.example.backend.repository.SwapRequestRepository;
import org.example.backend.repository.UserRepository;
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
    }

    @Override
    public void updateStatus(Long requestId, String status) {
        SwapRequest request = swapRequestRepository.findById(requestId)
                .orElseThrow(() -> new CustomException("Request not found"));
        request.setStatus(RequestStatus.valueOf(status));
        swapRequestRepository.save(request);
    }

    @Override
    public List<SwapRequestDTO> getIncomingRequests(Long userId) {

        List<SwapRequest> requests = swapRequestRepository.findAll().stream()
                .filter(r -> r.getReceiver().getId().equals(userId))
                .toList();

        return requests.stream().map(req -> {
            SwapRequestDTO dto = modelMapper.map(req, SwapRequestDTO.class);
            dto.setSenderId(req.getSender().getId());
            dto.setReceiverId(req.getReceiver().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SwapRequestDTO> getOutgoingRequests(Long userId) {
        List<SwapRequest> requests = swapRequestRepository.findAll().stream()
                .filter(r -> r.getSender().getId().equals(userId))
                .toList();

        return requests.stream().map(req -> {
            SwapRequestDTO dto = modelMapper.map(req, SwapRequestDTO.class);
            dto.setSenderId(req.getSender().getId());
            dto.setReceiverId(req.getReceiver().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}