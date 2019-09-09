package com.wityorestaurant.modules.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wityorestaurant.modules.orderservice.model.CancelledOrderItem;

@Repository
public interface CancelledOrderRepository extends JpaRepository<CancelledOrderItem, Long>{

}
