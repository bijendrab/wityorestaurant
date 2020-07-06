package com.wityorestaurant.modules.customerdata;

public class CustomerCartAddOnItems {
    private Long cartAddOnId;
    private int itemId;
    private String itemName;
    private double price;

    public Long getCartAddOnId() {
        return cartAddOnId;
    }

    public void setCartAddOnId(Long cartAddOnId) {
        this.cartAddOnId = cartAddOnId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
