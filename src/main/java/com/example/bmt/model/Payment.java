package com.example.bmt.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity @Table(name="payments")
public class Payment {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private Double amount;
  private String method;
  private String status;
  private LocalDateTime transactionDate;

  @OneToOne @JoinColumn(name="order_id") private Order order;

  @Column(name="created_at", updatable=false) @CreationTimestamp
  private LocalDateTime createdAt;
  @Column(name="updated_at") @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public Double getAmount(){return amount;} public void setAmount(Double a){this.amount=a;}
  public String getMethod(){return method;} public void setMethod(String m){this.method=m;}
  public String getStatus(){return status;} public void setStatus(String s){this.status=s;}
  public LocalDateTime getTransactionDate(){return transactionDate;} public void setTransactionDate(LocalDateTime t){this.transactionDate=t;}
  public Order getOrder(){return order;} public void setOrder(Order o){this.order=o;}
  public LocalDateTime getCreatedAt(){return createdAt;}
  public LocalDateTime getUpdatedAt(){return updatedAt;}
}