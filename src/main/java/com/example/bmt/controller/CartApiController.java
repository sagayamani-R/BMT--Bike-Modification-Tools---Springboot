package com.example.bmt.controller;

import com.example.bmt.model.User;
import com.example.bmt.repository.UserRepository;
import com.example.bmt.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/cart")
public class CartApiController {

    private final CartService cartService;
    private final UserRepository userRepo;

    public CartApiController(CartService cartService, UserRepository userRepo) {
        this.cartService = cartService;
        this.userRepo = userRepo;
    }

    /** Resolve current user or return null if guest */
    private User currentUser(Authentication auth) {
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            return null;
        }
        return userRepo.findByUsername(auth.getName()).orElse(null);
    }

    /** Return cart item count for header badge */
    @GetMapping("/count")
    @ResponseBody
    public int count(Authentication auth, HttpSession session) {
        User user = currentUser(auth);
        return (user != null)
                ? cartService.count(user)
                : cartService.count(session);
    }
}