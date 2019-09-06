package com.wityorestaurant.modules.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wityorestaurant.modules.orderservice.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, String>{

}
