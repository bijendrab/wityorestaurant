package com.wityorestaurant.modules.cart.model;

import java.util.List;

public class RestaurantCartItemInput {
    private String productId;
    private List<RestaurantCartAddOnItems> restaurantCartAddOnItemsList;
    private String restaurantSelectQuantityOption;
    private Integer restaurantSelectQuantityAmount;
    private Long tableId;
    private String orderTaker;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<RestaurantCartAddOnItems> getRestaurantCartAddOnItemsList() {
        return restaurantCartAddOnItemsList;
    }

    public void setRestaurantCartAddOnItemsList(List<RestaurantCartAddOnItems> restaurantCartAddOnItemsList) {
        this.restaurantCartAddOnItemsList = restaurantCartAddOnItemsList;
    }

    public String getRestaurantSelectQuantityOption() {
        return restaurantSelectQuantityOption;
    }

    public void setRestaurantSelectQuantityOption(String restaurantSelectQuantityOption) {
        this.restaurantSelectQuantityOption = restaurantSelectQuantityOption;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getOrderTaker() {
        return orderTaker;
    }

    public void setOrderTaker(String orderTaker) {
        this.orderTaker = orderTaker;
    }

    public Integer getRestaurantSelectQuantityAmount() {
        return restaurantSelectQuantityAmount;
    }

    public void setRestaurantSelectQuantityAmount(Integer restaurantSelectQuantityAmount) {
        this.restaurantSelectQuantityAmount = restaurantSelectQuantityAmount;
    }
}
