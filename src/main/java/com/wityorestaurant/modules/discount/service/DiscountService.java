package com.wityorestaurant.modules.discount.service;

import java.util.List;
import com.wityorestaurant.modules.discount.model.Discount;

public interface DiscountService {

	List<Discount> getAllDiscounts();

	public Discount insertDiscountRecord(Discount discount);
	
	public Discount updateDiscount(Discount discount);
	
	public boolean enableDisableDiscount(int discountId);
}
