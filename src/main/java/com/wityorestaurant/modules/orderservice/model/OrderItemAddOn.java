package com.wityorestaurant.modules.orderservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "order_item_addon")
public class OrderItemAddOn implements Serializable {
    @Id
    @Column(name = "orderItemAddOnId")
    private String orderItemAddOnId;

    @Column(name = "itemId")
    private int itemId;

    @Column(name = "itemName")
    private String itemName;

    //@Min(value = 50, message = "Minimum value should be greater than 50")
    @Column(name = "price")
    private double price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "orderItemId")
    private OrderItem orderItem;

    public String getOrderItemAddOnId() {
        return orderItemAddOnId;
    }

    public void setOrderItemAddOnId(String orderItemAddOnId) {
        this.orderItemAddOnId = orderItemAddOnId;
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

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
