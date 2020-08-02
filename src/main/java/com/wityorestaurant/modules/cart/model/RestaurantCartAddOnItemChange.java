package com.wityorestaurant.modules.cart.model;

import java.util.List;

public class RestaurantCartAddOnItemChange {
    private Long tableId;
    private Long cartItemId;
    private List<RestaurantCartAddOnItems> restaurantCartAddOnItemsList;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public List<RestaurantCartAddOnItems> getRestaurantCartAddOnItemsList() {
        return restaurantCartAddOnItemsList;
    }

    public void setRestaurantCartAddOnItemsList(List<RestaurantCartAddOnItems> restaurantCartAddOnItemsList) {
        this.restaurantCartAddOnItemsList = restaurantCartAddOnItemsList;
    }
}
