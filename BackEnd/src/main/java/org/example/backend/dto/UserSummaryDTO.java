package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserSummaryDTO {
    private Long userId;
    private String userName;
    private String email;
    private String bio;
    private Double averageRating;
    private Long totalSkills;
    private Long pendingIncomingRequests;
    private Long totalOutgoingRequests;
}