package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReviewDTO {
    private Long id;
    private Long reviewerId;
    private Long revieweeId;
    private int rating;
    private String comment;
}