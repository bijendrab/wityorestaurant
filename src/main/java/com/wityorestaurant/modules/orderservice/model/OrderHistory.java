package com.wityorestaurant.modules.orderservice.model;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
@Entity
@Table(name = "restaurant_order_history")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderHistoryId")
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderHistoryId")
    private Long orderHistoryId;

    @Lob
    @Column(name = "orders")
    private String orders;

    @Column(name = "tableId")
    private Long tableId;

    @Column(name = "paymentStatus")
    private Boolean paymentStatus;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name="totalCost")
    private double totalCost;


    @ManyToOne()
    @JoinColumn(name = "restId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "restId")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("restId")
    private RestaurantDetails restaurantDetails;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date orderCreationTime;

    private LocalDateTime orderHistoryTime;


    public Long getOrderHistoryId() {
        return orderHistoryId;
    }

    public void setOrderHistoryId(Long orderHistoryId) {
        this.orderHistoryId = orderHistoryId;
    }


    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Date getOrderCreationTime() {
        return orderCreationTime;
    }

    public void setOrderCreationTime(Date orderCreationTime) {
        this.orderCreationTime = orderCreationTime;
    }

    public LocalDateTime getOrderHistoryTime() {
        return orderHistoryTime;
    }

    public void setOrderHistoryTime(LocalDateTime orderHistoryTime) {
        this.orderHistoryTime = orderHistoryTime;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}

