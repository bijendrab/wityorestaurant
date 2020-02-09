package com.wityorestaurant.modules.payment.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingDetailResponse {

	private List<BillingDetailItem> billingDetailItems;

	private Map<String, Map<Double, List<String>>> taxCharges = new HashMap<>();

	private Map<String, Double> totalCalculatedTaxed = new HashMap<>();

	private float serviceCharge = 0.0F;
	private float packagingCharge = 0.0F;
	private float overallDiscount = 0.0F;

	public Map<String, Double> getTotalCalculatedTaxed() {
		return totalCalculatedTaxed;
	}

	public void setTotalCalculatedTaxed(Map<String, Double> totalCalculatedTaxed) {
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

	public float getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(float serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public float getPackagingCharge() {
		return packagingCharge;
	}

	public void setPackagingCharge(float packagingCharge) {
		this.packagingCharge = packagingCharge;
	}

	public float getOverallDiscount() {
		return overallDiscount;
	}

	public void setOverallDiscount(float overallDiscount) {
		this.overallDiscount = overallDiscount;
	}
	
	

}
