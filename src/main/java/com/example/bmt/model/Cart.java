package com.example.bmt.model;

import jakarta.persistence.*;
import java.util.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity @Table(name="cart")
public class Cart {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @OneToOne @JoinColumn(name="user_id") private User user;
  @OneToMany(mappedBy="cart", cascade=CascadeType.ALL, orphanRemoval=true)
  private List<CartItem> items = new ArrayList<>();

  @Column(name="created_at", updatable=false) @CreationTimestamp
  private LocalDateTime createdAt;
  @Column(name="updated_at") @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public User getUser(){return user;} public void setUser(User u){this.user=u;}
  public List<CartItem> getItems(){return items;} public void setItems(List<CartItem> i){this.items=i;}
  public LocalDateTime getCreatedAt(){return createdAt;}
  public LocalDateTime getUpdatedAt(){return updatedAt;}
}