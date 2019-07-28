package com.wityorestaurant.modules.menu.dto;

import java.util.List;

import com.wityorestaurant.modules.menu.model.Product;

public class RestaurantMenuDto {
	
	List<Product> restaurantMenu;

	public List<Product> getRestaurantMenu() {
		return restaurantMenu;
	}

	public void setRestaurantMenu(List<Product> restaurantMenu) {
		this.restaurantMenu = restaurantMenu;
	}

}
