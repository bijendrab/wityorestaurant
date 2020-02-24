package com.wityorestaurant.modules.cart.service;

import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import com.wityorestaurant.modules.menu.model.Product;

public interface CartService {

    RestaurantCartItem addCart(Product product, String quantityOption, Long tableId, String orderTaker);

    RestaurantCartItem updateCart(Product product, String quantityOption, Long tableId, String orderTaker);

    String deleteCartItemById(Long cartItemId);

    String reduceCartItem(String productId, String quantityOption);

    RestaurantCart getCart(Long tableId);
}
