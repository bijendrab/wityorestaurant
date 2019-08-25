package com.wityorestaurant.modules.cart.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

@Entity
@Table(name = "restaurant_cart_item")
public class RestaurantCartItem {
	
	private Long cartItemId;
	private String itemName;
	private String quantityOption;
	private int quantity;
	private double price;
	private Boolean immediateStatus=Boolean.FALSE;

	@ManyToOne
	@JoinColumn(name = "cart_id")
	@JsonIgnore
	private RestaurantCart cart;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private RestaurantDetails restaurant;
	
	@ManyToOne
	@JoinColumn(name = "table_id")
	private RestTable table;
	
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;

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

	public RestaurantDetails getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantDetails restaurant) {
		this.restaurant = restaurant;
	}

	public RestTable getTable() {
		return table;
	}

	public void setTable(RestTable table) {
		this.table = table;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
