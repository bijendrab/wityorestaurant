package com.wityorestaurant.modules.cart.service;

import java.util.List;
import java.util.Set;
import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.cart.model.RestaurantCartAddOnItemChange;
import com.wityorestaurant.modules.cart.model.RestaurantCartAddOnItems;
import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import com.wityorestaurant.modules.cart.model.RestaurantCartItemInput;
import com.wityorestaurant.modules.cart.model.RestaurantCartItemQuanityChange;
import com.wityorestaurant.modules.menu.model.AddOnItems;
import com.wityorestaurant.modules.menu.model.Product;

public interface CartService {

    String addToCart(RestaurantCartItemInput restaurantCartItemInput);

    String updateQuantityItemInCart(RestaurantCartItemQuanityChange restaurantCartItemQuanityChange);

    String updateAddOnItemsInCart(RestaurantCartAddOnItemChange restaurantCartAddOnItemChange);

    String deleteCartItemById(Long cartItemId);

    RestaurantCart getCart(Long tableId);
}
