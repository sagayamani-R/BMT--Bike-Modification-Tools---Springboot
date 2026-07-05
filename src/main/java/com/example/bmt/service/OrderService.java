package com.example.bmt.service;

import com.example.bmt.model.*;
import com.example.bmt.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {
  private final OrderRepository orderRepo;
  private final OrderItemRepository itemRepo;
  private final CartService cartService;
  private final AddressRepository addressRepo;
  private final ProductRepository productRepo;
  private final PaymentRepository paymentRepo;

  public OrderService(OrderRepository o, OrderItemRepository oi, CartService c,
                      AddressRepository a, ProductRepository p, PaymentRepository pay) {
    this.orderRepo = o; this.itemRepo = oi; this.cartService = c;
    this.addressRepo = a; this.productRepo = p; this.paymentRepo = pay;
  }

  @Transactional
  public Order placeOrder(User user, Long addressId, String paymentMethod) {
    Address addr = addressRepo.findById(addressId).orElseThrow();
    Cart cart = cartService.getOrCreateCart(user);
    if (cart.getItems().isEmpty()) throw new IllegalStateException("Cart is empty");

    Order order = new Order();
    order.setUser(user);
    order.setOrderDate(LocalDateTime.now());
    order.setStatus("PENDING");
    order.setShippingAddress(addr);

    double total = 0.0;
    List<OrderItem> orderItems = new ArrayList<>();
    for (CartItem ci : cart.getItems()) {
      Product p = ci.getProduct();
      if (p.getStock() < ci.getQuantity()) throw new IllegalArgumentException("Insufficient stock");
      p.setStock(p.getStock() - ci.getQuantity());
      productRepo.save(p);

      OrderItem oi = new OrderItem();
      oi.setOrder(order);
      oi.setProduct(p);
      oi.setQuantity(ci.getQuantity());
      oi.setPrice(p.getPrice());
      orderItems.add(oi);
      total += p.getPrice() * ci.getQuantity();
    }

    order.setTotalAmount(total);
    order.setItems(orderItems);
    order = orderRepo.save(order);
    itemRepo.saveAll(orderItems);

    Payment payment = new Payment();
    payment.setOrder(order);
    payment.setAmount(total);
    payment.setMethod(paymentMethod);
    payment.setStatus("SUCCESS");
    payment.setTransactionDate(LocalDateTime.now());
    paymentRepo.save(payment);

    order.setStatus("PAID");
    orderRepo.save(order);

    cart.getItems().clear();
    return order;
  }

  public List<Order> orderHistory(Long userId) {
    return orderRepo.findByUserIdOrderByOrderDateDesc(userId);
  }
}
