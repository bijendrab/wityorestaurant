package com.wityorestaurant.modules.discount.service;

import com.wityorestaurant.modules.discount.model.Discount;

public interface DiscountService {

	public Discount insertDiscountRecord(Discount discount);
	
	public Discount updateDiscount(Discount discount);
	
	public boolean enableDisableDiscount(int discountId);
}
