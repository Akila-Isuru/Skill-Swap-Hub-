package org.example.backend.controller;

import org.example.backend.dto.ReviewDTO;
import org.example.backend.service.ReviewService;
import org.example.backend.utill.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<APIResponse<String>> addReview(@RequestBody ReviewDTO dto) {
        reviewService.addReview(dto);
        return new ResponseEntity<>(new APIResponse<>(201, "Review Added Successfully", null), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse<List<ReviewDTO>>> getReviews(@PathVariable Long userId) {
        return new ResponseEntity<>(new APIResponse<>(200, "Success", reviewService.getReviewsForUser(userId)), HttpStatus.OK);
    }
    @GetMapping("/average/{userId}")
    public ResponseEntity<APIResponse<Double>> getAverage(@PathVariable Long userId) {
        return new ResponseEntity<>(new APIResponse<>(200, "Success", reviewService.getAverageRating(userId)), HttpStatus.OK);
    }
}