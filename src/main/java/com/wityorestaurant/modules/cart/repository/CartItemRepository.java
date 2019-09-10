package com.wityorestaurant.modules.cart.repository;

import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<RestaurantCartItem, Long> {

}
