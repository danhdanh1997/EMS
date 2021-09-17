package com.xuandanh.ems.service;

import com.xuandanh.ems.domain.OrderItem;
import com.xuandanh.ems.repository.OrderItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemsService {
    private OrderItemsRepository orderItemsRepository;

    public void addOrderedProducts(OrderItem orderItem) {
        orderItemsRepository.save(orderItem);
    }
}
