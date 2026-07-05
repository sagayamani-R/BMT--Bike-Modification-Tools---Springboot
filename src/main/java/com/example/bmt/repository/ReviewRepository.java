package com.example.bmt.repository;

import com.example.bmt.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Random reviews (for home page)
    @Query(value = "SELECT * FROM reviews ORDER BY RANDOM()", nativeQuery = true)
    Page<Review> findRandomReviews(Pageable pageable);

    // Reviews by product category id (for product-specific pages)
    @Query("SELECT r FROM Review r WHERE r.product.category.id = :categoryId")
    Page<Review> findByCategoryId(Long categoryId, Pageable pageable);
}
