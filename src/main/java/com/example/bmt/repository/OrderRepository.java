package com.example.bmt.repository;

import com.example.bmt.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
}
