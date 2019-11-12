package com.wityorestaurant.modules.discount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wityorestaurant.modules.discount.model.Discount;
import com.wityorestaurant.modules.discount.repository.DiscountRepository;
import com.wityorestaurant.modules.discount.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	private DiscountRepository discountRepository;

	public Discount insertDiscountRecord(Discount discount) {

		return discountRepository.save(discount);
	}

}
