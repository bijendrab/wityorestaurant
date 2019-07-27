package com.wityorestaurant.modules.restaurant.service;

import com.wityorestaurant.modules.restaurant.dto.RegistrationDTO;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;

import java.util.List;

public interface RestaurantUserService {
    List<RestaurantUser> fetchAllUsers();
    RestaurantUser saveUser(RegistrationDTO newUser);
    void removeUser(String username);
    RestaurantUser getUserByUsername(String username);
}
