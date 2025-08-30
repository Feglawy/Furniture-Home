package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.dto.ReviewDTO;
import com.egronx.furniturehome.entity.Review;
import com.egronx.furniturehome.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public void addReview(@RequestBody ReviewDTO reviewDTO) {
        reviewService.addReview(reviewDTO);
    }

    @GetMapping("/{id}")
    public List<ReviewDTO> getReviews(@PathVariable Long id) {
        return reviewService.getReviews(id);
    }
}
