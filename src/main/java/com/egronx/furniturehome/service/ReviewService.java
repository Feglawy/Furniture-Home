package com.egronx.furniturehome.service;

import com.egronx.furniturehome.dto.ReviewDTO;
import com.egronx.furniturehome.entity.Cart;
import com.egronx.furniturehome.entity.Product;
import com.egronx.furniturehome.entity.Review;
import com.egronx.furniturehome.entity.User;
import com.egronx.furniturehome.repository.ProductRepository;
import com.egronx.furniturehome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    UserRepository userRepository;
    ProductRepository productRepository;

    @Autowired
    public ReviewService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public void addReview(ReviewDTO reviewDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
        Product product = productRepository.findById(reviewDTO.getProductId());
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        user.addReview(review);
        userRepository.save(user);
    }

    public List<ReviewDTO> getReviews(Long productId) {
        Product product = productRepository.findById(productId);
        List<Review> reviews = new ArrayList<>(product.getReviews());
        List<ReviewDTO> reviewDTOs = new ArrayList<>(reviews.size());
        for (Review review : reviews) {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setProductId(productId);
            reviewDTO.setComment(review.getComment());
            reviewDTO.setRating(review.getRating());
            reviewDTOs.add(reviewDTO);
        }
        return reviewDTOs;
    }
}
