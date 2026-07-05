package com.example.bmt.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity @Table(name="addresses")
public class Address {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String street; private String city; private String state; private String zip; private String country;
  @ManyToOne @JoinColumn(name="user_id") private User user;

  @Column(name="created_at", updatable=false) @CreationTimestamp
  private LocalDateTime createdAt;
  @Column(name="updated_at") @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getStreet(){return street;} public void setStreet(String s){this.street=s;}
  public String getCity(){return city;} public void setCity(String c){this.city=c;}
  public String getState(){return state;} public void setState(String s){this.state=s;}
  public String getZip(){return zip;} public void setZip(String z){this.zip=z;}
  public String getCountry(){return country;} public void setCountry(String c){this.country=c;}
  public User getUser(){return user;} public void setUser(User u){this.user=u;}
  public LocalDateTime getCreatedAt(){return createdAt;}
  public LocalDateTime getUpdatedAt(){return updatedAt;}
}