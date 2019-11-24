package com.wityorestaurant.modules.discount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wityorestaurant.modules.discount.model.Discount;
import com.wityorestaurant.modules.discount.repository.DiscountRepository;
import com.wityorestaurant.modules.discount.service.DiscountService;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	private DiscountRepository discountRepository;

	@Autowired
	private RestaurantUserRepository userRepo;

	public Discount insertDiscountRecord(Discount discount) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		RestaurantUser restUser = userRepo.findByUsername(auth.getName());
		RestaurantDetails restaurant = restUser.getRestDetails();
		discount.setRestaurant(restaurant);
		discount.getDiscountedItems().forEach(item -> {
			item.setDiscount(discount);
			item.getProduct().setDiscountItem(item);
		});
		return discountRepository.save(discount);
	}

}
