package com.wityorestaurant.modules.discount.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.wityorestaurant.modules.menu.model.Product;

@Entity
public class DiscountItem implements Serializable {

	private static final long serialVersionUID = 6121574713993131921L;

	@Id
	private Integer discountItemId;
	@ManyToMany
	@Cascade(CascadeType.PERSIST)
	@JoinTable(name = "discount_item_product", joinColumns = @JoinColumn(name = "discount_item_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
	private Set<Product> products;
	@ManyToOne
	@JoinColumn(name = "discount_id")
	private Discount discount;

	public Integer getDiscountItemId() {
		return discountItemId;
	}

	public void setDiscountItemId(Integer discountItemId) {
		this.discountItemId = discountItemId;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}
