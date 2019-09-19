package com.wityorestaurant.modules.orderservice.dto;

import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;

public class UpdateOrderItemDTO {

    private String orderItemId;
    private int quantity;
    private String quantityOption;
    private CustomerInfoDTO customer;

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getQuantityOption() {
        return quantityOption;
    }

    public void setQuantityOption(String quantityOption) {
        this.quantityOption = quantityOption;
    }

    public CustomerInfoDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerInfoDTO customer) {
        this.customer = customer;
    }
}
