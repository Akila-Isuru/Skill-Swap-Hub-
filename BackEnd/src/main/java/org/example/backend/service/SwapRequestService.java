package org.example.backend.service;

import org.example.backend.dto.SwapRequestDTO;

import java.util.List;

public interface SwapRequestService {
    void sendRequest(SwapRequestDTO dto);
    void updateStatus(Long requestId, String status);
    List<SwapRequestDTO> getRequestsForUser(Long userId);
}