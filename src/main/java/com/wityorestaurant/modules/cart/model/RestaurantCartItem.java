package com.wityorestaurant.modules.cart.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wityorestaurant.modules.config.model.RestTable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "restaurant_cart_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cartItemId")
public class RestaurantCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
    private String itemName;
    private String quantityOption;
    private int quantity;
    private double price;
    private Boolean immediateStatus = Boolean.FALSE;
    private LocalDateTime addItemToCartTime;
    private LocalDateTime updateItemInCartTime;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private RestaurantCart cart;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "table_id")
    private RestTable table;


    private String productJson;

    private String orderTaker;

    @OneToMany(mappedBy = "restaurantCartItem", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RestaurantCartAddOnItems> restaurantCartAddOnItems;

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

    public Boolean getImmediateStatus() {
        return immediateStatus;
    }

    public void setImmediateStatus(Boolean immediateStatus) {
        this.immediateStatus = immediateStatus;
    }

    public RestaurantCart getCart() {
        return cart;
    }

    public void setCart(RestaurantCart cart) {
        this.cart = cart;
    }

    public RestTable getTable() {
        return table;
    }

    public void setTable(RestTable table) {
        this.table = table;
    }

    public String getOrderTaker() {
        return orderTaker;
    }

    public void setOrderTaker(String orderTaker) {
        this.orderTaker = orderTaker;
    }

    public String getProductJson() {
        return productJson;
    }

    public void setProductJson(String productJson) {
        this.productJson = productJson;
    }

    public Set<RestaurantCartAddOnItems> getRestaurantCartAddOnItems() {
        return restaurantCartAddOnItems;
    }

    public void setRestaurantCartAddOnItems(Set<RestaurantCartAddOnItems> restaurantCartAddOnItems) {
        this.restaurantCartAddOnItems = restaurantCartAddOnItems;
    }

    public LocalDateTime getAddItemToCartTime() {
        return addItemToCartTime;
    }

    public void setAddItemToCartTime(LocalDateTime addItemToCartTime) {
        this.addItemToCartTime = addItemToCartTime;
    }

    public LocalDateTime getUpdateItemInCartTime() {
        return updateItemInCartTime;
    }

    public void setUpdateItemInCartTime(LocalDateTime updateItemInCartTime) {
        this.updateItemInCartTime = updateItemInCartTime;
    }
}
