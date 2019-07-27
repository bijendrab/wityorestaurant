package com.wityorestaurant.modules.restaurant.dto;

import java.util.Map;

public class RestaurantListDto {
	
	private Map<Long, String> restaurantDetails;

	public Map<Long, String> getRestaurantDetails() {
		return restaurantDetails;
	}

	public void setRestaurantDetails(Map<Long, String> restaurantDetails) {
		this.restaurantDetails = restaurantDetails;
	}
}
