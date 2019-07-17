package com.wityorestaurant.modules.restaurant.service;

import com.wityorestaurant.modules.restaurant.dto.RegistrationDTO;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;

import java.util.List;

public interface RestaurantUserService {
    public List<RestaurantUser> fetchAllUsers();
    public RestaurantUser saveUser(RegistrationDTO newUser);
    public String removeUser(String username);
    public RestaurantUser getUserByUsername(String username);
}
