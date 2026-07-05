package com.example.bmt.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity @Table(name="orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime orderDate;
  private String status;
  private Double totalAmount;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> items = new ArrayList<>();
  @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
  private Payment payment;
  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address shippingAddress;

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;
  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime d) {
    this.orderDate = d;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String s) {
    this.status = s;
  }

  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double t) {
    this.totalAmount = t;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User u) {
    this.user = u;
  }

  public List<OrderItem> getItems() {
    return items;
  }

  public void setItems(List<OrderItem> i) {
    this.items = i;
  }

  public Payment getPayment() {
    return payment;
  }

  public void setPayment(Payment p) {
    this.payment = p;
  }

  public Address getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(Address a) {
    this.shippingAddress = a;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}