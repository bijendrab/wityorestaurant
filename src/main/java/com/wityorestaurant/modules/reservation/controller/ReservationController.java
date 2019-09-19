package com.wityorestaurant.modules.reservation.controller;

import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.reservation.dto.ReservationDetailsDto;
import com.wityorestaurant.modules.reservation.service.MangerReservationService;
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
    MangerReservationService reservationManagerImpl;


    @Autowired
    RestaurantUserRepository userRepo;

    @PostMapping("/{restaurantId}/check-reservation")
    public ResponseEntity<?> checkReserve(@PathVariable("restaurantId") Long restId, @RequestBody CustomerInfoDTO custInfo) {
        return new ResponseEntity<>(reservationManagerImpl.isTableAssigned(custInfo, restId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{restaurantId}/reserve")
    public ResponseEntity<?> reserve(@PathVariable("restaurantId") Long restId, @RequestBody ReservationDetailsDto checkRequestDTO) {
        return new ResponseEntity<>(reservationManagerImpl.reserveResult(checkRequestDTO, restId), HttpStatus.ACCEPTED);
    }
}
