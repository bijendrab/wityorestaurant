package com.wityorestaurant.modules.cart.service;

import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import com.wityorestaurant.modules.menu.model.Product;

public interface CartService {

	public RestaurantCartItem addOrUpdateCart(Product product, String quantityOption, Long tableId, String orderTaker);
	public String deleteCartItemById(Long cartItemId);
	public String reduceCartItem(String productId, String quantityOption);
	public RestaurantCart getCart();
}
