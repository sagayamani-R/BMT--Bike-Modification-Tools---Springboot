package com.example.bmt.controller;

import com.example.bmt.model.Cart;
import com.example.bmt.model.User;
import com.example.bmt.model.Product;
import com.example.bmt.repository.UserRepository;
import com.example.bmt.repository.ProductRepository;
import com.example.bmt.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

  private final CartService cartService;
  private final UserRepository userRepo;
  private final ProductRepository productRepo;

  public CartController(CartService cartService, UserRepository userRepo, ProductRepository productRepo) {
    this.cartService = cartService;
    this.userRepo = userRepo;
    this.productRepo = productRepo;
  }

  /** Resolve current user or return null if guest */
  private User currentUser(Authentication auth) {
    if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
      return null;
    }
    return userRepo.findByUsername(auth.getName()).orElse(null);
  }

  /** View cart page */
  @GetMapping
  public String view(Authentication auth, HttpSession session, Model model) {
    User user = currentUser(auth);
    Cart cart = (user != null)
            ? cartService.getOrCreateCart(user)
            : cartService.getOrCreateCart(session);

    model.addAttribute("cart", cart);
    model.addAttribute("total", cartService.total(cart));

    // Add random products (e.g. 4 suggestions)
    List<Product> randomProducts = productRepo.findRandomProducts(4);
    model.addAttribute("randomProducts", randomProducts);

    return "cart";
  }

  /** Add item to cart (page reload flow) */
  @PostMapping("/add/{id}")
  public String addToCart(@PathVariable("id") Long productId,
                          @RequestParam(defaultValue = "1") int qty,
                          Authentication auth,
                          HttpSession session,
                          HttpServletRequest request) {

    User user = currentUser(auth);
    if (user != null) {
      cartService.addItem(user, productId, qty);
    } else {
      cartService.addItem(session, productId, qty);
    }

    String referer = request.getHeader("Referer");
    if (referer != null && !referer.isBlank()) {
      return "redirect:" + referer;
    } else {
      return "redirect:/";
    }
  }

  /** Add item to cart (AJAX/JSON flow) */
  @PostMapping("/add/{id}/json")
  @ResponseBody
  public Map<String,Object> addToCartJson(@PathVariable("id") Long productId,
                                          @RequestParam(defaultValue = "1") int qty,
                                          Authentication auth,
                                          HttpSession session) {
    User user = currentUser(auth);
    if (user != null) {
      cartService.addItem(user, productId, qty);
    } else {
      cartService.addItem(session, productId, qty);
    }

    Cart cart = (user != null)
            ? cartService.getOrCreateCart(user)
            : cartService.getOrCreateCart(session);

    return Map.of("success", true,
            "count", cartService.count(cart),
            "total", cartService.total(cart));
  }

  /** Remove item */
  @PostMapping("/remove/{id}")
  public String remove(Authentication auth, HttpSession session, @PathVariable("id") Long itemId) {
    User user = currentUser(auth);
    if (user != null) {
      cartService.removeItem(user, itemId);
    } else {
      cartService.removeItem(session, itemId);
    }
    return "redirect:/cart";
  }

  /** Remove item (AJAX/JSON) */
  @PostMapping("/remove/{id}/json")
  @ResponseBody
  public Map<String,Object> removeJson(Authentication auth, HttpSession session, @PathVariable("id") Long itemId) {
    User user = currentUser(auth);
    if (user != null) {
      cartService.removeItem(user, itemId);
    } else {
      cartService.removeItem(session, itemId);
    }

    Cart cart = (user != null)
            ? cartService.getOrCreateCart(user)
            : cartService.getOrCreateCart(session);

    return Map.of("success", true,
            "count", cartService.count(cart),
            "total", cartService.total(cart));
  }

  /** Increase quantity */
  @PostMapping("/inc/{id}")
  public String increment(Authentication auth, HttpSession session, @PathVariable("id") Long itemId) {
    User user = currentUser(auth);
    if (user != null) {
      cartService.changeQuantity(user, itemId, +1);
    } else {
      cartService.changeQuantity(session, itemId, +1);
    }
    return "redirect:/cart";
  }

  /** Increase quantity (AJAX/JSON) */
  @PostMapping("/inc/{id}/json")
  @ResponseBody
  public Map<String,Object> incrementJson(Authentication auth, HttpSession session, @PathVariable("id") Long itemId) {
    User user = currentUser(auth);
    if (user != null) {
      cartService.changeQuantity(user, itemId, +1);
    } else {
      cartService.changeQuantity(session, itemId, +1);
    }

    Cart cart = (user != null)
            ? cartService.getOrCreateCart(user)
            : cartService.getOrCreateCart(session);

    return Map.of("success", true,
            "count", cartService.count(cart),
            "total", cartService.total(cart));
  }

  /** Decrease quantity */
  @PostMapping("/dec/{id}")
  public String decrement(Authentication auth, HttpSession session, @PathVariable("id") Long itemId) {
    User user = currentUser(auth);
    if (user != null) {
      cartService.changeQuantity(user, itemId, -1);
    } else {
      cartService.changeQuantity(session, itemId, -1);
    }
    return "redirect:/cart";
  }

  /** Decrease quantity (AJAX/JSON) */
  @PostMapping("/dec/{id}/json")
  @ResponseBody
  public Map<String,Object> decrementJson(Authentication auth, HttpSession session, @PathVariable("id") Long itemId) {
    User user = currentUser(auth);
    if (user != null) {
      cartService.changeQuantity(user, itemId, -1);
    } else {
      cartService.changeQuantity(session, itemId, -1);
    }

    Cart cart = (user != null)
            ? cartService.getOrCreateCart(user)
            : cartService.getOrCreateCart(session);

    return Map.of("success", true,
            "count", cartService.count(cart),
            "total", cartService.total(cart));
  }
}
