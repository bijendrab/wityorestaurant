package com.wityorestaurant.modules.discount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityorestaurant.modules.discount.model.Discount;
import com.wityorestaurant.modules.discount.service.DiscountService;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/discount")
public class DiscountController {

	@Autowired
	private DiscountService discountService;

	@Autowired
	RestaurantUserRepository userRepo;

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/adddiscount")
	public ResponseEntity<?> addDiscount(@RequestBody Discount discount) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		RestaurantUser restUser = userRepo.findByUsername(auth.getName());
		RestaurantDetails restaurant = restUser.getRestDetails();
		discount.setRestaurant(restaurant);
		return new ResponseEntity<Discount>(discountService.insertDiscountRecord(discount), HttpStatus.OK);
	}
}
