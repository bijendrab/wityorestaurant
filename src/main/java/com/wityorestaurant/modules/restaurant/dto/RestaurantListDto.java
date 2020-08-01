package com.wityorestaurant.modules.restaurant.dto;

import java.util.List;

public class RestaurantListDto {

    private List<RestaurantBasicDTO> restaurantDetails;

    public List<RestaurantBasicDTO> getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(List<RestaurantBasicDTO> restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }
}
