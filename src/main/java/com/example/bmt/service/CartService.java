package com.example.bmt.service;

import com.example.bmt.model.*;
import com.example.bmt.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {
  private final CartRepository cartRepo;
  private final CartItemRepository itemRepo;
  private final ProductRepository productRepo;

  public CartService(CartRepository c, CartItemRepository i, ProductRepository p) {
    this.cartRepo = c;
    this.itemRepo = i;
    this.productRepo = p;
  }

  // ---------------- USER CART ----------------
  public Cart getOrCreateCart(User user) {
    return cartRepo.findByUserId(user.getId()).orElseGet(() -> {
      Cart cart = new Cart();
      cart.setUser(user);
      return cartRepo.save(cart);
    });
  }

  public void addItem(User user, Long productId, int qty) {
    Cart cart = getOrCreateCart(user);
    Product product = productRepo.findById(productId).orElseThrow();

    if (product.getStock() < qty) throw new IllegalArgumentException("Insufficient stock");

    Optional<CartItem> existing = cart.getItems().stream()
            .filter(i -> i.getProduct().getId().equals(productId))
            .findFirst();

    if (existing.isPresent()) {
      CartItem ci = existing.get();
      ci.setQuantity(ci.getQuantity() + qty);
    } else {
      CartItem ci = new CartItem();
      ci.setCart(cart);
      ci.setProduct(product);
      ci.setQuantity(qty);
      cart.getItems().add(ci);
    }

    cartRepo.save(cart);
  }

  public void removeItem(User user, Long itemId) {
    Cart cart = getOrCreateCart(user);
    cart.getItems().removeIf(i -> i.getId().equals(itemId));
    cartRepo.save(cart);
  }

  public int count(User user) {
    return getOrCreateCart(user).getItems().stream()
            .mapToInt(CartItem::getQuantity).sum();
  }

  public double total(User user) {
    return getOrCreateCart(user).getItems().stream()
            .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity()).sum();
  }

  // ---------------- SESSION CART (GUEST) ----------------
  public Cart getOrCreateCart(HttpSession session) {
    Cart cart = (Cart) session.getAttribute("CART");
    if (cart == null) {
      cart = new Cart();
      cart.setItems(new ArrayList<>());
      session.setAttribute("CART", cart);
    }
    return cart;
  }

  public void addItem(HttpSession session, Long productId, int qty) {
    Cart cart = getOrCreateCart(session);
    Product product = productRepo.findById(productId).orElseThrow();

    if (product.getStock() < qty) throw new IllegalArgumentException("Insufficient stock");

    Optional<CartItem> existing = cart.getItems().stream()
            .filter(i -> i.getProduct().getId().equals(productId))
            .findFirst();

    if (existing.isPresent()) {
      CartItem ci = existing.get();
      ci.setQuantity(ci.getQuantity() + qty);
    } else {
      CartItem ci = new CartItem();
      ci.setCart(cart);
      ci.setProduct(product);
      ci.setQuantity(qty);
      // synthetic ID for guest items
      if (ci.getId() == null) ci.setId(System.nanoTime());
      cart.getItems().add(ci);
    }

    session.setAttribute("CART", cart);
  }

  public void removeItem(HttpSession session, Long itemId) {
    Cart cart = getOrCreateCart(session);
    cart.getItems().removeIf(i -> itemId.equals(i.getId()));
    session.setAttribute("CART", cart);
  }

  public int count(HttpSession session) {
    Cart cart = getOrCreateCart(session);
    if (cart == null || cart.getItems() == null) {
      return 0;
    }
    return cart.getItems().stream()
            .mapToInt(CartItem::getQuantity)
            .sum();
  }


  public double total(HttpSession session) {
    Cart cart = getOrCreateCart(session);
    return total(cart);
  }

  public double total(Cart cart) {
    return cart.getItems().stream()
            .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity()).sum();
  }

  // ---------------- MERGE LOGIC ----------------
  public void mergeCarts(Cart guestCart, Cart userCart) {
    for (CartItem item : guestCart.getItems()) {
      Optional<CartItem> existing = userCart.getItems().stream()
              .filter(i -> i.getProduct().getId().equals(item.getProduct().getId()))
              .findFirst();

      if (existing.isPresent()) {
        CartItem ci = existing.get();
        ci.setQuantity(ci.getQuantity() + item.getQuantity());
      } else {
        CartItem ci = new CartItem();
        ci.setCart(userCart);
        ci.setProduct(item.getProduct());
        ci.setQuantity(item.getQuantity());
        userCart.getItems().add(ci);
      }
    }
    cartRepo.save(userCart);
  }

  public void changeQuantity(User user, Long itemId, int delta) {
    Cart cart = getOrCreateCart(user);
    cart.getItems().forEach(i -> {
      if (i.getId().equals(itemId)) {
        int q = Math.max(1, i.getQuantity() + delta);
        i.setQuantity(q);
      }
    });
    cartRepo.save(cart);
  }

  public void changeQuantity(HttpSession session, Long itemId, int delta) {
    Cart cart = getOrCreateCart(session);
    cart.getItems().forEach(i -> {
      if (i.getId() != null && i.getId().equals(itemId)) {
        int q = Math.max(1, i.getQuantity() + delta);
        i.setQuantity(q);
      }
    });
    session.setAttribute("CART", cart);
  }

  public int count(Cart cart) {
    return cart.getItems().stream()
            .mapToInt(CartItem::getQuantity)
            .sum();
  }

}
