package org.example.backend.repository;

import org.example.backend.entity.enums.RequestStatus;
import org.example.backend.entity.SwapRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SwapRequestRepository extends JpaRepository<SwapRequest, Long> {
    List<SwapRequest> findByReceiverId(Long receiverId);
    List<SwapRequest> findByReceiverIdAndStatus(Long receiverId, RequestStatus status);
    List<SwapRequest> findBySenderId(Long senderId);
}
