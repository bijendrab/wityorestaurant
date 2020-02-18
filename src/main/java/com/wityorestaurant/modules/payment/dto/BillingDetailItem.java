package com.wityorestaurant.modules.payment.dto;

import com.wityorestaurant.modules.tax.model.TaxProfile;

public class BillingDetailItem {

	private Long orderId;
	private String productId;
	private String itemName;
	private Integer quantity;
	private String quantityOption;
	private Double price;
	private Double value;
	private TaxProfile appliedTaxProfile;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getQuantityOption() {
		return quantityOption;
	}

	public void setQuantityOption(String quantityOption) {
		this.quantityOption = quantityOption;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public TaxProfile getAppliedTaxProfile() {
		return appliedTaxProfile;
	}

	public void setAppliedTaxProfile(TaxProfile appliedTaxProfile) {
		this.appliedTaxProfile = appliedTaxProfile;
	}

}
