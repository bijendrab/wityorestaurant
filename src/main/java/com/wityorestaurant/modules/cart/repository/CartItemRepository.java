package com.wityorestaurant.modules.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wityorestaurant.modules.cart.model.RestaurantCartItem;

@Repository
public interface CartItemRepository extends JpaRepository<RestaurantCartItem, Long>{

}
