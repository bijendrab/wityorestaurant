package com.wityorestaurant.modules.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.payment.service.PaymentService;

@RestController("/api/payment")
public class PaymentController {

	private PaymentService paymentService;

	public PaymentController() {
	}

	@Autowired
	public PaymentController(PaymentService paymentService) {
		super();
		this.paymentService = paymentService;
	}

	@GetMapping("/billing/{restaurantId}")
	public ResponseEntity<?> getOrderPaymentSummary(@PathVariable("restaurantId") Long restId,
			@RequestBody CustomerInfoDTO customerInfoDTO) {
		return ResponseEntity.status(HttpStatus.FOUND)
				.body(paymentService.getOrderPaymentSummary(customerInfoDTO, restId));
	}

}
