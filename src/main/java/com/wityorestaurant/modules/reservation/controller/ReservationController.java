package com.wityorestaurant.modules.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.reservation.dto.ReservationDetailsDto;
import com.wityorestaurant.modules.reservation.service.MangerReservationService;
import com.wityorestaurant.modules.reservation.service.ReservationService;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/reservation")
public class ReservationController {
    @Autowired
    ReservationService reservationServiceImpl;

    @Autowired
    MangerReservationService reservationManagerImpl;


    @Autowired
    RestaurantUserRepository userRepo;

    @PostMapping("/{restaurantId}/check-reservation")
    public ResponseEntity<?> checkReserve(@PathVariable("restaurantId") Long restId,@RequestBody CustomerInfoDTO custInfo){
        Integer res = reservationManagerImpl.isTableAssigned(custInfo);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PostMapping("/{restaurantId}/reserve")
    public ResponseEntity<?> reserve(@PathVariable("restaurantId") Long restId,@RequestBody ReservationDetailsDto checkRequestDTO){
        return new ResponseEntity<>(reservationManagerImpl.reserveResult(checkRequestDTO), HttpStatus.ACCEPTED);
    }
}
