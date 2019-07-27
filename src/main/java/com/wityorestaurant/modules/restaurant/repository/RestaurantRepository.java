package com.wityorestaurant.modules.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantDetails, Long> {

}
