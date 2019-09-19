package com.wityorestaurant.modules.cart.repository;

import com.wityorestaurant.modules.cart.model.RestaurantCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<RestaurantCart, Long> {

}
