package com.example.bmt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity @Table(name="users")
public class User {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;

  @NotBlank @Column(unique=true) private String username;
  @NotBlank @Column(unique=true) private String phone;
  @NotBlank @Column(unique=true) private String email;
  @NotBlank @Size(min=6) private String password;

  private String role;
  private String img;

  @OneToOne(mappedBy="user", cascade=CascadeType.ALL) private Cart cart;
  @OneToMany(mappedBy="user", cascade=CascadeType.ALL) private List<Order> orders = new ArrayList<>();
  @OneToMany(mappedBy="user", cascade=CascadeType.ALL) private List<Address> addresses = new ArrayList<>();

  @Column(name="created_at", updatable=false) @CreationTimestamp
  private LocalDateTime createdAt;
  @Column(name="updated_at") @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getUsername(){return username;} public void setUsername(String u){this.username=u;}
  public String getPhone(){return phone;} public void setPhone(String p){this.phone=p;}
  public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
  public String getPassword(){return password;} public void setPassword(String p){this.password=p;}
  public String getRole(){return role;} public void setRole(String r){this.role=r;}
  public String getImg() { return img; } public void setImg(String img) { this.img = img; }
  public Cart getCart(){return cart;} public void setCart(Cart c){this.cart=c;}
  public List<Order> getOrders(){return orders;} public void setOrders(List<Order> o){this.orders=o;}
  public List<Address> getAddresses(){return addresses;} public void setAddresses(List<Address> a){this.addresses=a;}
  public LocalDateTime getCreatedAt(){return createdAt;}
  public LocalDateTime getUpdatedAt(){return updatedAt;}
}