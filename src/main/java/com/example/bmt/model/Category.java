package com.example.bmt.model;

import jakarta.persistence.*;
import java.util.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity @Table(name="categories")
public class Category {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(unique=true) private String name;
  @OneToMany(mappedBy="category") private List<Product> products = new ArrayList<>();

  @Column(name="created_at", updatable=false) @CreationTimestamp
  private LocalDateTime createdAt;
  @Column(name="updated_at") @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getName(){return name;} public void setName(String n){this.name=n;}
  public List<Product> getProducts(){return products;} public void setProducts(List<Product> p){this.products=p;}
  public LocalDateTime getCreatedAt(){return createdAt;}
  public LocalDateTime getUpdatedAt(){return updatedAt;}
}