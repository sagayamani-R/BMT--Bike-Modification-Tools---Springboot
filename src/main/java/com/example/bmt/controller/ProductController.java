package com.example.bmt.controller;

import com.example.bmt.model.Product;
import com.example.bmt.repository.ProductRepository;
import com.example.bmt.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;
  private final ProductRepository productRepository;

  public ProductController(ProductService productService, ProductRepository productRepository) {
    this.productService = productService;
    this.productRepository = productRepository;
  }

  @GetMapping
  public String list(@RequestParam(required = false) String q,
                     @RequestParam(required = false) String category,
                     Model model) {
    List<Product> products = productService.search(q, category);
    model.addAttribute("products", products);
    model.addAttribute("q", q);
    model.addAttribute("category", category);

    if (category != null) {
      switch (category.toLowerCase()) {
        case "helmets":
          return "helmets";     // renders helmets.html
        case "gears":
          return "gears";       // renders gears.html
        case "accessories":
          return "Accessories"; // renders accessories.html
        default:
          return "product-list"; // fallback
      }
    }

    return "product-list"; // default when no category is provided
  }
}
