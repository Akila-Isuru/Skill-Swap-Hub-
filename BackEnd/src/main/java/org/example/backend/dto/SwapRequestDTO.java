package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwapRequestDTO {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String message;
    private String status;
    // PENDING, ACCEPTED, REJECTED, COMPLETED
}