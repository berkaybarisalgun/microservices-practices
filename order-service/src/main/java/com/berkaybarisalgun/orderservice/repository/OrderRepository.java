package com.berkaybarisalgun.orderservice.repository;

import com.berkaybarisalgun.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
