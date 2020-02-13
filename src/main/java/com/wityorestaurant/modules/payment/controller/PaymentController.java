package com.wityorestaurant.modules.payment.controller;

import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.payment.service.PaymentService;
@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentController {

	private PaymentService paymentService;
	@Autowired
	private RestaurantUserRepository restaurantUserRepository;

	public PaymentController() {
	}

	@Autowired
	public PaymentController(PaymentService paymentService) {
		super();
		this.paymentService = paymentService;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/billing/{tableId}")
	public ResponseEntity<?> getOrderPaymentSummary(@PathVariable("tableId") Long tableId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
		return ResponseEntity.status(HttpStatus.FOUND)
				.body(paymentService.getOrderPaymentSummary(restaurantUser.getRestDetails().getRestId(), tableId));
	}

}
