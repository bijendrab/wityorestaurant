package com.wityorestaurant.modules.cart.service;

import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.menu.model.Product;

public interface CartService {

	public String addOrUpdateCart(Product product, String quantityOption);
	public String deleteCartItemById(Long cartItemId);
	public String reduceCartItem(String productId, String quantityOption);
	public RestaurantCart getCart();
}
