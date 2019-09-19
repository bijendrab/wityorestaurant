package com.wityorestaurant.modules.restaurant.dto;

import java.util.List;

public class RestaurantListDto {

    private List<RestaurantIdNameDto> restaurantDetails;

    public List<RestaurantIdNameDto> getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(List<RestaurantIdNameDto> restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }


}
