package com.example.bmt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity @Table(name="admins")
public class Admin {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;

  @NotBlank @Column(unique=true) private String username;
  @NotBlank @Pattern(regexp="^[6-9][0-9]{9}$") @Column(unique=true) private String phone;
  @NotBlank @Email @Column(unique=true) private String email;
  @NotBlank @Size(min=6) private String password;

  @Enumerated(EnumType.STRING)
  private AdminRole role;

  @Column(name="created_at", updatable=false) @CreationTimestamp
  private LocalDateTime createdAt;
  @Column(name="updated_at") @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getUsername(){return username;} public void setUsername(String u){this.username=u;}
  public String getPhone(){return phone;} public void setPhone(String p){this.phone=p;}
  public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
  public String getPassword(){return password;} public void setPassword(String p){this.password=p;}
  public AdminRole getRole(){return role;} public void setRole(AdminRole r){this.role=r;}
  public LocalDateTime getCreatedAt(){return createdAt;}
  public LocalDateTime getUpdatedAt(){return updatedAt;}
}