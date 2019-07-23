package com.wityorestaurant.modules.user.service;

import com.wityorestaurant.modules.user.dto.RegistrationDTO;
import com.wityorestaurant.modules.user.model.RestaurantUser;

import java.util.List;

public interface RestaurantUserService {
    List<RestaurantUser> fetchAllUsers();
    RestaurantUser saveUser(RegistrationDTO newUser);
    void removeUser(String username);
    RestaurantUser getUserByUsername(String username);
}
