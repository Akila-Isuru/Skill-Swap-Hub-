package org.example.backend.service;

import org.example.backend.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    void addReview(ReviewDTO dto);
    List<ReviewDTO> getReviewsForUser(Long userId);
    Double getAverageRating(Long userId);
}
