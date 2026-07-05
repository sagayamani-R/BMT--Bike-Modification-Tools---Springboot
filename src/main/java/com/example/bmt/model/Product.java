package com.example.bmt.model;

import jakarta.persistence.*;
import java.util.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity @Table(name="products")
public class Product {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String name;
  @Column(length=2000) private String description;
  private Double price;
  private Integer stock;
  private String imageUrl;

  @ManyToOne @JoinColumn(name="category_id") private Category category;
  @OneToMany(mappedBy="product", cascade=CascadeType.ALL) private List<Review> reviews = new ArrayList<>();

  @Column(name="created_at", updatable=false) @CreationTimestamp
  private LocalDateTime createdAt;
  @Column(name="updated_at") @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getName(){return name;} public void setName(String n){this.name=n;}
  public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
  public Double getPrice(){return price;} public void setPrice(Double p){this.price=p;}
  public Integer getStock(){return stock;} public void setStock(Integer s){this.stock=s;}
  public String getImageUrl(){return imageUrl;} public void setImageUrl(String u){this.imageUrl=u;}
  public Category getCategory(){return category;} public void setCategory(Category c){this.category=c;}
  public List<Review> getReviews(){return reviews;} public void setReviews(List<Review> r){this.reviews=r;}
  public LocalDateTime getCreatedAt(){return createdAt;}
  public LocalDateTime getUpdatedAt(){return updatedAt;}
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ProductFeature> features = new ArrayList<>();

  public List<ProductFeature> getFeatures() { return features; }
  public void setFeatures(List<ProductFeature> features) { this.features = features; }

}