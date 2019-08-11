package com.wityorestaurant.modules.customerdata;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

public class CustomerCartItems {
    private Long cartItemId;
    private String itemName;
    private String quantityOption;
    private int quantity;
    private double price;
    @Lob
    private String productJson;

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantityOption() {
        return quantityOption;
    }

    public void setQuantityOption(String quantityOption) {
        this.quantityOption = quantityOption;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductJson() {
        return productJson;
    }

    public void setProductJson(String productJson) {
        this.productJson = productJson;
    }
}