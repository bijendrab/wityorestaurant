package com.wityorestaurant.modules.reservation.controller;

import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.reservation.dto.CheckRequestDTO;
import com.wityorestaurant.modules.reservation.service.ReservationManager;
import com.wityorestaurant.modules.reservation.service.ReservationService;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/reservation")
public class ReservationController {
    @Autowired
    ReservationService reservationServiceImpl;

    @Autowired
    ReservationManager reservationManagerImpl;


    @Autowired
    RestaurantUserRepository userRepo;

    @PostMapping("/{restaurantId}/checkReserve")
    public ResponseEntity<?> checkReserve(@PathVariable("restaurantId") Long restId,@RequestBody CustomerInfoDTO custInfo){
        Integer res = reservationManagerImpl.isTableAssigned(custInfo);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PostMapping("/{restaurantId}/reserve")
    public ResponseEntity<?> reserve(@PathVariable("restaurantId") Long restId,@RequestBody CheckRequestDTO checkRequestDTO){
        return new ResponseEntity<>(reservationManagerImpl.reserveResult(checkRequestDTO), HttpStatus.ACCEPTED);
    }
}
