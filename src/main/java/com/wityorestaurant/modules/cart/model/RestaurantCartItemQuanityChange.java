package com.wityorestaurant.modules.cart.model;

public class RestaurantCartItemQuanityChange {
    private Long tableId;
    private Long cartItemId;
    private Integer restaurantSelectQuantityAmount;

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

    public Integer getRestaurantSelectQuantityAmount() {
        return restaurantSelectQuantityAmount;
    }

    public void setRestaurantSelectQuantityAmount(Integer restaurantSelectQuantityAmount) {
        this.restaurantSelectQuantityAmount = restaurantSelectQuantityAmount;
    }
}
