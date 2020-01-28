package com.wityorestaurant.modules.discount.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.model.ProductQuantityOptions;

@Entity
public class DiscountItem implements Serializable {

	private static final long serialVersionUID = 6121574713993131921L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int discountItemId;

	@OneToMany
	@JoinTable(name = "discount_item_product", joinColumns = @JoinColumn(name = "discount_item_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
	private Set<Product> products;

	@OneToMany
	@JoinTable(name = "discount_item_product_quantity", joinColumns = @JoinColumn(name = "discount_item_id"), inverseJoinColumns = @JoinColumn(name = "qoid"))
    private Set<ProductQuantityOptions> selectedItemQuantityOptions;
	

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "discountId")
	private Discount discount;

	public int getDiscountItemId() {
		return discountItemId;
	}

	public void setDiscountItemId(int discountItemId) {
		this.discountItemId = discountItemId;
	}

	public Set<ProductQuantityOptions> getSelectedItemQuantityOptions() {
		return selectedItemQuantityOptions;
	}

	public void setSelectedItemQuantityOptions(Set<ProductQuantityOptions> selectedItemQuantityOptions) {
		this.selectedItemQuantityOptions = selectedItemQuantityOptions;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

}
