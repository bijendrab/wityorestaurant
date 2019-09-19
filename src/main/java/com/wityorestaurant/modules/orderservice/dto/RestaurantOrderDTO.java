package com.wityorestaurant.modules.orderservice.dto;

import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;

import java.util.List;

public class RestaurantOrderDTO {
    private List<RestaurantCartItem> cartItems;
    private CustomerInfoDTO customer;

    public List<RestaurantCartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<RestaurantCartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public CustomerInfoDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerInfoDTO customer) {
        this.customer = customer;
    }
}
