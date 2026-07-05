package com.example.bmt.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity @Table(name="cart_items")
public class CartItem {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private Integer quantity;

  @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="cart_id", nullable=false)
  private Cart cart;
  @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="product_id", nullable=false)
  private Product product;

  @Column(name="created_at", updatable=false) @CreationTimestamp
  private LocalDateTime createdAt;
  @Column(name="updated_at") @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public Integer getQuantity(){return quantity;} public void setQuantity(Integer q){this.quantity=q;}
  public Cart getCart(){return cart;} public void setCart(Cart c){this.cart=c;}
  public Product getProduct(){return product;} public void setProduct(Product p){this.product=p;}
  public LocalDateTime getCreatedAt(){return createdAt;}
  public LocalDateTime getUpdatedAt(){return updatedAt;}
}