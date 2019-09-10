package com.wityorestaurant.modules.menu.dto;

import com.wityorestaurant.modules.menu.model.Product;

import java.util.List;

public class RestaurantMenuDto {

    List<Product> restaurantMenu;

    public List<Product> getRestaurantMenu() {
        return restaurantMenu;
    }

    public void setRestaurantMenu(List<Product> restaurantMenu) {
        this.restaurantMenu = restaurantMenu;
    }

}
