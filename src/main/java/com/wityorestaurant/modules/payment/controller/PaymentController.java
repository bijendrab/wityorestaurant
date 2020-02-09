package com.wityorestaurant.modules.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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

	public PaymentController() {
	}

	@Autowired
	public PaymentController(PaymentService paymentService) {
		super();
		this.paymentService = paymentService;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/billing/{restaurantId}")
	public ResponseEntity<?> getOrderPaymentSummary(@PathVariable("restaurantId") Long restId,
			@RequestBody CustomerInfoDTO customerInfoDTO) {
		return ResponseEntity.status(HttpStatus.FOUND)
				.body(paymentService.getOrderPaymentSummary(customerInfoDTO, restId));
	}

}
