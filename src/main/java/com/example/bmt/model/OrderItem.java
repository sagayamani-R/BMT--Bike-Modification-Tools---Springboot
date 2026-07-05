package com.example.bmt.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity @Table(name="order_items")
public class OrderItem {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private Integer quantity;
  private Double price;

  @ManyToOne @JoinColumn(name="order_id") private Order order;
  @ManyToOne @JoinColumn(name="product_id") private Product product;

  @Column(name="created_at", updatable=false) @CreationTimestamp
  private LocalDateTime createdAt;
  @Column(name="updated_at") @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public Integer getQuantity(){return quantity;} public void setQuantity(Integer q){this.quantity=q;}
  public Double getPrice(){return price;} public void setPrice(Double p){this.price=p;}
  public Order getOrder(){return order;} public void setOrder(Order o){this.order=o;}
  public Product getProduct(){return product;} public void setProduct(Product p){this.product=p;}
  public LocalDateTime getCreatedAt(){return createdAt;}
  public LocalDateTime getUpdatedAt(){return updatedAt;}
}