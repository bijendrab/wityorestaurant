package com.wityorestaurant.wityorestaurant;

import org.springframework.beans.factory.annotation.Autowired;

import com.wityorestaurant.modules.discount.model.Discount;
import com.wityorestaurant.modules.discount.repository.DiscountRepository;

public class testjava {

	@Autowired
	private static DiscountRepository discountRepository;
	
	public static void main(String[] args) {
		System.out.println("strted ");
		// TODO Auto-generated method stub
		Discount obj = new Discount();
		obj.setDaysOfMonth("vhh");
		obj.setDaysOfWeek("vhh");
		obj.setDiscountType("huhj");
		discountRepository.save(obj);
		System.out.println("endddddd ");
		
	}

}
