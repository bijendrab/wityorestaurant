package com.wityorestaurant.modules.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orderitem")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = -5213196255613404458L;

    @Id
    private String orderItemId;

    private LocalDateTime orderCreationTime;

    private int quantity;

    private double price;

    private String status;

    private String itemName;

    /*private Date OrderEndTime;*/

    private double waitTime;

    private String quantityOption;

    private Boolean isVeg;



    private Boolean immediateStatus;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval=true)
    private Set<OrderItemAddOn> orderItemAddOns = new HashSet<>(0);


    public Boolean getImmediateStatus() {
        return immediateStatus;
    }

    public void setImmediateStatus(Boolean immediateStatus) {
        this.immediateStatus = immediateStatus;
    }


    @Lob
    private String customerCartItems;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private Order order;

    private Boolean specialDiscount = false;
    private float specialDiscountValue = 0F;


    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public LocalDateTime getOrderCreationTime() {
        return orderCreationTime;
    }

    public void setOrderCreationTime(LocalDateTime orderCreationTime) {
        this.orderCreationTime = orderCreationTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean getIsVeg() {
        return this.isVeg;
    }

    public void setIsVeg(Boolean isVeg) {
        this.isVeg = isVeg;
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

    public Boolean getSpecialDiscount() {
        return specialDiscount;
    }

    public void setSpecialDiscount(Boolean specialDiscount) {
        this.specialDiscount = specialDiscount;
    }

    public float getSpecialDiscountValue() {
        return specialDiscountValue;
    }

    public void setSpecialDiscountValue(float specialDiscountValue) {
        this.specialDiscountValue = specialDiscountValue;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Set<OrderItemAddOn> getOrderItemAddOns() {
        return orderItemAddOns;
    }

    public void setOrderItemAddOns(Set<OrderItemAddOn> orderItemAddOns) {
        this.orderItemAddOns = orderItemAddOns;
    }
}
