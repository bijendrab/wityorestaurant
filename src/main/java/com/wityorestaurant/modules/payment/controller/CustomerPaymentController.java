package com.wityorestaurant.modules.payment.controller;

import com.google.gson.Gson;
import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.payment.dto.BillingDetailResponse;
import com.wityorestaurant.modules.payment.service.PaymentService;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/billing")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerPaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestaurantUserRepository restaurantUserRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    @PostMapping("/forCustomer/{restaurantId}")
    public ResponseEntity<?> getOrderPaymentSummary(@PathVariable("restaurantId") Long restId, @RequestBody CustomerInfoDTO customerInfoDTO) {
        Reservation customerReservation = reservationRepository.getByCustomerId(new Gson().toJson(customerInfoDTO), restId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(paymentService.getOrderPaymentSummary(restId, customerReservation.getRelatedTable().getId()));
    }
}
