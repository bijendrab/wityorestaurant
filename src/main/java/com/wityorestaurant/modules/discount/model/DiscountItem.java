package com.wityorestaurant.modules.discount.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.model.ProductQuantityOptions;

@Entity
public class DiscountItem implements Serializable {

	private static final long serialVersionUID = 6121574713993131921L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer discountItemId;

	@OneToOne
	private Product product;

	@OneToMany
	@JoinTable(name = "discount_item_product_quantity", 
	joinColumns = @JoinColumn(name = "discount_item_id"), inverseJoinColumns = @JoinColumn(name = "qoid"))
	private Set<ProductQuantityOptions> selectedItemQuantityOptions;

	@ManyToOne
	@JoinColumn(name = "discount_id")
	private Discount discount;

	public Set<ProductQuantityOptions> getSelectedItemQuantityOptions() {
		return selectedItemQuantityOptions;
	}

	public void setSelectedItemQuantityOptions(Set<ProductQuantityOptions> selectedItemQuantityOptions) {
		this.selectedItemQuantityOptions = selectedItemQuantityOptions;
	}

	public Integer getDiscountItemId() {
		return discountItemId;
	}

	public void setDiscountItemId(Integer discountItemId) {
		this.discountItemId = discountItemId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

}
