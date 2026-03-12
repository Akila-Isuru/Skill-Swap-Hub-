package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SwapRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender; // Request එක යවන කෙනා

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver; // Request එක ලැබෙන කෙනා

    private String message;

    @Enumerated(EnumType.STRING)
    private RequestStatus status; // PENDING, ACCEPTED, REJECTED, COMPLETED

    private LocalDateTime createdAt = LocalDateTime.now();
}

