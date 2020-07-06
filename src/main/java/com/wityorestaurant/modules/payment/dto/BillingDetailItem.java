package com.wityorestaurant.modules.payment.dto;

import java.util.List;
import java.util.Set;
import com.wityorestaurant.modules.orderservice.model.OrderItemAddOn;
import com.wityorestaurant.modules.tax.model.TaxProfile;

public class BillingDetailItem {

	private String orderId;
	private String productId;
	private String itemName;
	private Integer quantity;
	private String quantityOption;
	private Double price;
	private Double value;
	private Double specialDiscount;
	private TaxProfile appliedTaxProfile;
	private Set<OrderItemAddOn> orderItemAddOns;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
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

	public Double getSpecialDiscount() {
		return specialDiscount;
	}

	public void setSpecialDiscount(Double specialDiscount) {
		this.specialDiscount = specialDiscount;
	}

	public TaxProfile getAppliedTaxProfile() {
		return appliedTaxProfile;
	}

	public void setAppliedTaxProfile(TaxProfile appliedTaxProfile) {
		this.appliedTaxProfile = appliedTaxProfile;
	}

	public Set<OrderItemAddOn> getOrderItemAddOns() {
		return orderItemAddOns;
	}

	public void setOrderItemAddOns(Set<OrderItemAddOn> orderItemAddOns) {
		this.orderItemAddOns = orderItemAddOns;
	}
}
