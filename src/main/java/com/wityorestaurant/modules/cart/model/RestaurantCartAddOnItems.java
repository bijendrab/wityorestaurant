package com.wityorestaurant.modules.cart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "restaurant_cart_AddOn_item")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cartAddOnId")
public class RestaurantCartAddOnItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartAddOnId")
    private Long cartAddOnId;

    @Column(name = "itemId")
    private int itemId;

    @Column(name = "itemName")
    private String itemName;

    @NotNull(message = "Please provide some price")
    //@Min(value = 50, message = "Minimum value should be greater than 50")
    @Column(name = "price")
    private double price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cartItemId")
    private RestaurantCartItem restaurantCartItem;

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

    public RestaurantCartItem getRestaurantCartItem() {
        return restaurantCartItem;
    }

    public void setRestaurantCartItem(RestaurantCartItem restaurantCartItem) {
        this.restaurantCartItem = restaurantCartItem;
    }
}
