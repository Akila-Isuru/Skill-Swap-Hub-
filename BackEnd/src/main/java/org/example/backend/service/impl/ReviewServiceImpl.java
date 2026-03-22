package org.example.backend.service.impl;

import org.example.backend.dto.ReviewDTO;
import org.example.backend.entity.Review;
import org.example.backend.entity.User;
import org.example.backend.exception.CustomException;
import org.example.backend.repository.ReviewRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.NotificationService;
import org.example.backend.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NotificationService notificationService;

    @Override
    public void addReview(ReviewDTO dto) {
        User reviewer = userRepository.findById(dto.getReviewerId()).orElseThrow(() -> new CustomException("Reviewer not found"));
        User reviewee = userRepository.findById(dto.getRevieweeId()).orElseThrow(() -> new CustomException("Reviewee not found"));

        Review review = new Review();
        review.setReviewer(reviewer);
        review.setReviewee(reviewee);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        notificationService.createNotification(
                dto.getRevieweeId(),
                "You received a new " + dto.getRating() + "-star review!",
                "REVIEW"
        );

        reviewRepository.save(review);
    }

    @Override
    public List<ReviewDTO> getReviewsForUser(Long userId) {
        List<Review> reviews = reviewRepository.findByRevieweeId(userId);


        return reviews.stream().map(r -> {
            ReviewDTO dto = new ReviewDTO();
            dto.setId(r.getId());
            dto.setReviewerId(r.getReviewer().getId());
            dto.setReviewerName(r.getReviewer().getName());
            dto.setRevieweeId(r.getReviewee().getId());
            dto.setRating(r.getRating());
            dto.setComment(r.getComment());
            return dto;
        }).collect(java.util.stream.Collectors.toList());
    }
    @Override
    public Double getAverageRating(Long userId) {
        List<Review> reviews = reviewRepository.findByRevieweeId(userId);
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }
}