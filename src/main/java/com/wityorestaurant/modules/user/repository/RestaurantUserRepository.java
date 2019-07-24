package com.wityorestaurant.modules.user.repository;

import com.wityorestaurant.modules.user.model.RestaurantUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantUserRepository extends CrudRepository<RestaurantUser, Long> {

    RestaurantUser findByUsername(String username);
    RestaurantUser findByUserId(Long userId);
}
