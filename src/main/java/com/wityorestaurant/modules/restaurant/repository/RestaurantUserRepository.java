package com.wityorestaurant.modules.restaurant.repository;

import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantUserRepository extends CrudRepository<RestaurantUser, Long> {

    public RestaurantUser findByUsername(String username);
    public RestaurantUser findByUserId(Long userId);
}
