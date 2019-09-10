package com.wityorestaurant.modules.orderservice.repository;

import com.wityorestaurant.modules.orderservice.model.OrderProcessing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProcessingRepository extends JpaRepository<OrderProcessing, String> {

}
