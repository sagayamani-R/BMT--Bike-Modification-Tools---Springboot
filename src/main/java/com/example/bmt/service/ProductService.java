package com.example.bmt.service;

import com.example.bmt.repository.ProductRepository;
import com.example.bmt.repository.CategoryRepository;
import com.example.bmt.model.Product;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
  private final ProductRepository productRepo;
  private final CategoryRepository categoryRepo;
  public ProductService(ProductRepository productRepo, CategoryRepository categoryRepo) {
    this.productRepo = productRepo; this.categoryRepo = categoryRepo;
  }
  public List<Product> listAll() { return productRepo.findAll(); }
  public List<Product> search(String q, String category) {
    if (category != null && !category.isBlank()) return productRepo.findByCategoryName(category);
    if (q != null && !q.isBlank()) return productRepo.searchByNameOrCategory(q);
    return productRepo.findAll();
  }
}
