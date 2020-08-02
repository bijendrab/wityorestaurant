package com.wityorestaurant.modules.payment.dto;

import java.util.ArrayList;
import java.util.List;

public class BillingDetailResponse {

	private List<BillingDetailItem> billingDetailItems;

	private List<TaxDetails> totalCalculatedTaxed = new ArrayList<>();

	private List<DiscountDetails> totalCalculatedDiscount= new ArrayList<>();

	private double serviceCharge = 0.0F;
	private float serviceChargePercent;
	private double addOnCharge = 0.0F;
	private float packagingCharge = 0.0F;
	private double tableDiscount = 0.0F;
	private float tableDiscountPercent;
	private double totalCostWithoutTaxAndDiscount;
	private double totalTax;
	private double totalItemsDiscount;
	private double totalDiscount;
	private double totalCost;


	public List<TaxDetails> getTotalCalculatedTaxed() {
		return totalCalculatedTaxed;
	}

	public void setTotalCalculatedTaxed(List<TaxDetails> totalCalculatedTaxed) {
		this.totalCalculatedTaxed = totalCalculatedTaxed;
	}

	public List<BillingDetailItem> getBillingDetailItems() {
		return billingDetailItems;
	}

	public void setBillingDetailItems(List<BillingDetailItem> billingDetailItems) {
		this.billingDetailItems = billingDetailItems;
	}

	public double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public float getPackagingCharge() {
		return packagingCharge;
	}

	public void setPackagingCharge(float packagingCharge) {
		this.packagingCharge = packagingCharge;
	}

	public double getTableDiscount() {
		return tableDiscount;
	}

	public void setTableDiscount(double tableDiscount) {
		this.tableDiscount = tableDiscount;
	}

	public float getServiceChargePercent() {
		return serviceChargePercent;
	}

	public void setServiceChargePercent(float serviceChargePercent) {
		this.serviceChargePercent = serviceChargePercent;
	}


	public float getTableDiscountPercent() {
		return tableDiscountPercent;
	}

	public void setTableDiscountPercent(float tableDiscountPercent) {
		this.tableDiscountPercent = tableDiscountPercent;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public List<DiscountDetails> getTotalCalculatedDiscount() {
		return totalCalculatedDiscount;
	}

	public void setTotalCalculatedDiscount(List<DiscountDetails> totalCalculatedDiscount) {
		this.totalCalculatedDiscount = totalCalculatedDiscount;
	}

	public double getTotalCostWithoutTaxAndDiscount() {
		return totalCostWithoutTaxAndDiscount;
	}

	public void setTotalCostWithoutTaxAndDiscount(double totalCostWithoutTaxAndDiscount) {
		this.totalCostWithoutTaxAndDiscount = totalCostWithoutTaxAndDiscount;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public double getTotalItemsDiscount() {
		return totalItemsDiscount;
	}

	public void setTotalItemsDiscount(double totalItemsDiscount) {
		this.totalItemsDiscount = totalItemsDiscount;
	}

	public double getAddOnCharge() {
		return addOnCharge;
	}

	public void setAddOnCharge(double addOnCharge) {
		this.addOnCharge = addOnCharge;
	}

	public double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
}
