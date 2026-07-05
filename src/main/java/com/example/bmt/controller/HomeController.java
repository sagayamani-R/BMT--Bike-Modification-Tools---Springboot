package com.example.bmt.controller;

import com.example.bmt.model.Review;
import com.example.bmt.service.ProductService;
import com.example.bmt.repository.ProductRepository;
import com.example.bmt.repository.ReviewRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
public class HomeController {
  private final ProductService productService;
  private final ProductRepository productRepository;
  private final ReviewRepository reviewRepo;

  public HomeController(ProductService ps, ProductRepository pr, ReviewRepository rr) {
    this.productService = ps;
    this.productRepository = pr;
    this.reviewRepo = rr;
  }

  @GetMapping({"/","/home"})
  public String home(Model model) {
    // products
    model.addAttribute("products", productService.listAll());

    // featured products
    model.addAttribute("featuredProducts",
            productRepository.findByCategoryName("Featured Products"));

    // offers
    model.addAttribute("offers",
            productRepository.findByCategoryName("Offers"));

    // fetch 4 random reviews
    Pageable limitFour = PageRequest.of(0, 4);
    List<Review> reviews = reviewRepo.findRandomReviews(limitFour).getContent();

    prepareReviews(reviews);
    model.addAttribute("reviews", reviews);

    return "home";
  }

  // helper method to compute stars and decrypted username
  private void prepareReviews(List<Review> reviews) {
    for (Review r : reviews) {
      int fullStars = (int) Math.floor(r.getRating());
      boolean halfStar = (r.getRating() % 1 >= 0.5);
      int emptyStars = 5 - fullStars - (halfStar ? 1 : 0);

      r.setFullStars(fullStars);
      r.setHalfStar(halfStar);
      r.setEmptyStars(emptyStars);

      // username is plain text, no decryption needed
      r.setDecryptedUsername(r.getUser().getUsername());
    }
  }
  @GetMapping("/contact")
  public String contactPage() {
    return "contact"; // looks for templates/contact.html
  }

  @GetMapping("/about")
  public String aboutPage() {
    return "about"; // looks for templates/contact.html
  }
}
