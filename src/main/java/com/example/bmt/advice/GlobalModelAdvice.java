package com.example.bmt.advice;

import com.example.bmt.model.User;
import com.example.bmt.repository.UserRepository;
import com.example.bmt.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
@ControllerAdvice
public class GlobalModelAdvice {
  private final CartService cartService;
  private final UserRepository userRepo;

  public GlobalModelAdvice(CartService c, UserRepository u){ this.cartService=c; this.userRepo=u; }

  @ModelAttribute
  public void addCartBadge(Authentication auth, Model model) {
    int count = 0;
    double total = 0;
    if (auth != null && auth.isAuthenticated()) {
      User user = userRepo.findByUsername(auth.getName()).orElse(null);
      if (user != null) { count = cartService.count(user); total = cartService.total(user); }
    }
    model.addAttribute("cartCount", count);
    model.addAttribute("cartTotal", total);
  }
}
