package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.dto.ReviewDTO;
import com.egronx.furniturehome.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            reviewService.addReview(reviewDTO);
            return ResponseEntity.accepted().build();
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReviews(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reviewService.getReviews(id));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
