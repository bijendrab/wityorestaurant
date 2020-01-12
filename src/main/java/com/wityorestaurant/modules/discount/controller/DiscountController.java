package com.wityorestaurant.modules.discount.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@GetMapping("/get-discounts")
	public ResponseEntity<?> getDiscounts(){
		return new ResponseEntity<>(discountService.getAllDiscounts(),HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/adddiscount")
	public ResponseEntity<?> addDiscount(@RequestBody Discount discount) {
		return new ResponseEntity<Discount>(discountService.insertDiscountRecord(discount), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')") 
	@PostMapping("/editdiscount")
	public ResponseEntity<?> updateDiscount(@RequestBody Discount discount) {
		return new ResponseEntity<Discount>(discountService.updateDiscount(discount), HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/enabledisablediscount")
	public ResponseEntity<?> enableDisableDiscount(@RequestBody int discountId) {
		return new ResponseEntity<>(discountService.enableDisableDiscount(discountId), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/deleteDiscount/{discountId}")
	public ResponseEntity<?> deleteDiscount(@PathVariable(value = "discountId") int discountId) {
		return new ResponseEntity<>(discountService.deleteDiscount(discountId), HttpStatus.ACCEPTED);
	}

}
