package com.wityorestaurant.modules.payment.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingDetailResponse {

	private List<BillingDetailItem> billingDetailItems;

	private Map<String, Map<Double, List<String>>> taxCharges = new HashMap<>();

	private List<TaxDetails> totalCalculatedTaxed = new ArrayList<>();

	private List<DiscountDetails> totalCalculatedDiscount= new ArrayList<>();

	private double serviceCharge = 0.0F;
	private float serviceChargePercent;
	private float packagingCharge = 0.0F;
	private double overallDiscount = 0.0F;
	private float overallDiscountPercent;
	private double totalCostWithoutTax;
	private double totalCostWithTax;
	private double totalCostWithDiscount;
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

	public Map<String, Map<Double, List<String>>> getTaxCharges() {
		return taxCharges;
	}

	public void setTaxCharges(Map<String, Map<Double, List<String>>> taxCharges) {
		this.taxCharges = taxCharges;
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

	public double getOverallDiscount() {
		return overallDiscount;
	}

	public void setOverallDiscount(double overallDiscount) {
		this.overallDiscount = overallDiscount;
	}

	public float getServiceChargePercent() {
		return serviceChargePercent;
	}

	public void setServiceChargePercent(float serviceChargePercent) {
		this.serviceChargePercent = serviceChargePercent;
	}


	public float getOverallDiscountPercent() {
		return overallDiscountPercent;
	}

	public void setOverallDiscountPercent(float overallDiscountPercent) {
		this.overallDiscountPercent = overallDiscountPercent;
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


	public double getTotalCostWithoutTax() {
		return totalCostWithoutTax;
	}

	public void setTotalCostWithoutTax(double totalCostWithoutTax) {
		this.totalCostWithoutTax = totalCostWithoutTax;
	}

	public double getTotalCostWithTax() {
		return totalCostWithTax;
	}

	public void setTotalCostWithTax(double totalCostWithTax) {
		this.totalCostWithTax = totalCostWithTax;
	}

	public double getTotalCostWithDiscount() {
		return totalCostWithDiscount;
	}

	public void setTotalCostWithDiscount(double totalCostWithDiscount) {
		this.totalCostWithDiscount = totalCostWithDiscount;
	}
}
