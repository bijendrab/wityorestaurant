package com.wityorestaurant.modules.customerdata;

import java.util.List;

public class CustomerOrderDTO {
    private List<CustomerCartItems> cartItems;
    private CustomerInfoDTO customer;

    public List<CustomerCartItems> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CustomerCartItems> cartItems) {
        this.cartItems = cartItems;
    }

    public CustomerInfoDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerInfoDTO customer) {
        this.customer = customer;
    }
}
