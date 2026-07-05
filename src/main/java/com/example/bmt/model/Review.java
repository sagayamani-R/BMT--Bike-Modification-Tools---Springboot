package com.example.bmt.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="reviews")
public class Review {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  private Double rating; // use Double for decimal ratings
  @Column(length=2000)
  private String comment;

  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name="product_id")
  private Product product;

  @Column(name="created_at", updatable=false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name="updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public String getUserImg() {
    return UserImg;
  }


  @Column(name="img")
  private String UserImg;

  // --- Transient fields for view rendering ---
  @Transient
  private int fullStars;

  @Transient
  private boolean halfStar;

  @Transient
  private int emptyStars;

  @Transient
  private String decryptedUsername;

  // getters and setters
  public Long getId(){ return id; }
  public void setId(Long id){ this.id = id; }

  public Double getRating(){ return rating; }
  public void setRating(Double rating){ this.rating = rating; }

  public String getComment(){ return comment; }
  public void setComment(String comment){ this.comment = comment; }

  public User getUser(){ return user; }
  public void setUser(User user){ this.user = user; }

  public Product getProduct(){ return product; }
  public void setProduct(Product product){ this.product = product; }

  public LocalDateTime getCreatedAt(){ return createdAt; }
  public LocalDateTime getUpdatedAt(){ return updatedAt; }

  public void setUserImg(String userImg) {
    UserImg = userImg;
  }

  public int getFullStars(){ return fullStars; }
  public void setFullStars(int fullStars){ this.fullStars = fullStars; }

  public boolean isHalfStar(){ return halfStar; }
  public void setHalfStar(boolean halfStar){ this.halfStar = halfStar; }

  public int getEmptyStars(){ return emptyStars; }
  public void setEmptyStars(int emptyStars){ this.emptyStars = emptyStars; }

  public String getDecryptedUsername(){ return decryptedUsername; }
  public void setDecryptedUsername(String decryptedUsername){ this.decryptedUsername = decryptedUsername; }
}
