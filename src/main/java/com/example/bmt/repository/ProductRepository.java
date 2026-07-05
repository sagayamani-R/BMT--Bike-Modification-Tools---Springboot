package com.example.bmt.repository;

import com.example.bmt.model.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByNameContainingIgnoreCase(String name);

  @Query("SELECT p FROM Product p WHERE LOWER(p.category.name) = LOWER(:categoryName)")
  List<Product> findByCategoryName(@Param("categoryName") String categoryName);

  @Query("""
    SELECT p FROM Product p
    WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%'))
       OR LOWER(p.category.name) LIKE LOWER(CONCAT('%', :q, '%'))
  """)
  List<Product> searchByNameOrCategory(@Param("q") String query);

  @Query(value = "SELECT * FROM products ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
  List<Product> findRandomProducts(@Param("limit") int limit);

}
