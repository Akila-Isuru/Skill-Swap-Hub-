package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer; // Review එක දාන කෙනා

    @ManyToOne
    @JoinColumn(name = "reviewee_id")
    private User reviewee; // Review එක ලබන කෙනා

    private int rating; // 1 to 5 stars
    private String comment;
}