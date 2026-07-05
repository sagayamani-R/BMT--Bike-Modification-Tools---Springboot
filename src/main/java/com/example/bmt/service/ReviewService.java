package com.example.bmt.service;

import com.example.bmt.model.ReviewDto;
import com.example.bmt.model.Review;
import com.example.bmt.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDto> getReviews(int page, int size) {
        // Fetch all reviews
        List<Review> allReviews = reviewRepository.findAll();

        // Shuffle for randomness
        Collections.shuffle(allReviews);

        // Limit to 'size' reviews
        return allReviews.stream()
                .limit(size)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ReviewDto toDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setComment(review.getComment());

        // Convert rating → stars
        double rating = review.getRating() != null ? review.getRating() : 0;
        int fullStars = (int) rating;
        boolean halfStar = (rating - fullStars) >= 0.5;
        int emptyStars = 5 - fullStars - (halfStar ? 1 : 0);

        dto.setFullStars(fullStars);
        dto.setHalfStar(halfStar);
        dto.setEmptyStars(emptyStars);

        //Populate username and image from User entity
        if (review.getUser() != null) {
            dto.setDecryptedUsername(review.getUser().getUsername());
            // Adjust this field name to match your User entity
            dto.setImg(review.getUser().getImg());
        }

        return dto;
    }

}
