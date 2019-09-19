package com.wityorestaurant.modules.cart.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cartId")
public class RestaurantCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private double totalPrice;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantDetails restaurant;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<RestaurantCartItem> cartItems;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<RestaurantCartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<RestaurantCartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public RestaurantDetails getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDetails restaurant) {
        this.restaurant = restaurant;
    }
}
