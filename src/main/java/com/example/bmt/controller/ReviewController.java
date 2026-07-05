package com.example.bmt.controller;

import com.example.bmt.model.Review;
import com.example.bmt.repository.ReviewRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
public class ReviewController {
    private final ReviewRepository reviewRepo;

    public ReviewController(ReviewRepository rr) {
        this.reviewRepo = rr;
    }

    @GetMapping("/reviews")
    public List<Review> getReviews(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "4") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Review> reviews = reviewRepo.findRandomReviews(pageable).getContent();
        prepareReviews(reviews);
        return reviews;
    }

    @GetMapping("/product/{categoryId}/reviews")
    public List<Review> getProductReviews(@PathVariable Long categoryId,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "4") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Review> reviews = reviewRepo.findByCategoryId(categoryId, pageable).getContent();
        prepareReviews(reviews);
        return reviews;
    }

    private void prepareReviews(List<Review> reviews) {
        for (Review r : reviews) {
            int fullStars = (int) Math.floor(r.getRating());
            boolean halfStar = (r.getRating() % 1 >= 0.5);
            int emptyStars = 5 - fullStars - (halfStar ? 1 : 0);

            r.setFullStars(fullStars);
            r.setHalfStar(halfStar);
            r.setEmptyStars(emptyStars);
            r.setDecryptedUsername(r.getUser().getUsername());
        }
    }
}
