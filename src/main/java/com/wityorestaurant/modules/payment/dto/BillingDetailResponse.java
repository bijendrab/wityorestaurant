package com.wityorestaurant.modules.payment.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingDetailResponse {

	private List<BillingDetailItem> billingDetailItems;

	private Map<String, Map<Double, List<String>>> taxCharges = new HashMap<>();

	private Map<String, Double> totalCalculatedTaxed = new HashMap<>();

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

}
