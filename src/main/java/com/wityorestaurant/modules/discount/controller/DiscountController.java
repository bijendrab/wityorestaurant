package com.wityorestaurant.modules.discount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityorestaurant.modules.discount.model.Discount;
import com.wityorestaurant.modules.discount.service.DiscountService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/discount")
public class DiscountController {

	@Autowired
	private DiscountService discountService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/adddiscount")
	public ResponseEntity<?> addDiscount(@RequestBody Discount discount) {
		return new ResponseEntity<Discount>(discountService.insertDiscountRecord(discount), HttpStatus.OK);
	}
}
