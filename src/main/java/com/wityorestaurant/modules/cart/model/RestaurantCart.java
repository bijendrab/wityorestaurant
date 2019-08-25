package com.wityorestaurant.modules.cart.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="cartId")
public class RestaurantCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;
	private double totalPrice;
	
	@OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
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
}
