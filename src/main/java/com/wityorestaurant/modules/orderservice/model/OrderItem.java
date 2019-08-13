package com.wityorestaurant.modules.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "orderitem")
public class OrderItem implements Serializable {

    @Id
    private String orderItemId;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date orderCreationTime;

    private int quantity;

    private double price;

    private String status;

    private String itemName;

    /*private Date OrderEndTime;*/

    private double waitTime;

    private String quantityOption;


    private Boolean immediateStatus;

    public Boolean getImmediateStatus() {
        return immediateStatus;
    }

    public void setImmediateStatus(Boolean immediateStatus) {
        this.immediateStatus = immediateStatus;
    }

    @JsonIgnore
    @Lob
    private String customerCartItems;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private Order order;


    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Date getOrderCreationTime() {
        return orderCreationTime;
    }

    public void setOrderCreationTime(Date orderCreationTime) {
        this.orderCreationTime = orderCreationTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    public String getQuantityOption() {
        return quantityOption;
    }

    public void setQuantityOption(String quantityOption) {
        this.quantityOption = quantityOption;
    }

    public String getCustomerCartItems() {
        return customerCartItems;
    }

    public void setCustomerCartItems(String customerCartItems) {
        this.customerCartItems = customerCartItems;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
