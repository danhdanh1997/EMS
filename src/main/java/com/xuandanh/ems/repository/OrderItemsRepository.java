package com.xuandanh.ems.repository;

import com.xuandanh.ems.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem,Integer> {
}
