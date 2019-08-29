package com.wityorestaurant.modules.orderservice.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
import com.wityorestaurant.modules.orderservice.service.OrderService;
import com.wityorestaurant.modules.orderservice.service.RestaurantOrderService;
import com.wityorestaurant.modules.reservation.dto.ReservationDetailsDto;
import com.wityorestaurant.modules.reservation.model.TimeSpan;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/restaurantOrder")
public class RestaurantOrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    RestaurantUserRepository restaurantUserRepository;
    
    @Autowired
    RestaurantOrderService restOrderService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getAllTableOrder")
    public ResponseEntity<?> getAllTableOrder(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<>(orderService.getAllTableOrderDetails(restaurantUser.getRestDetails().getRestId()), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/order-by-table/{tableId}")
    public ResponseEntity<?> getOrderByTable(@PathVariable Long tableId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<>(orderService.getRestaurantTableOrders(tableId, restaurantUser.getRestDetails().getRestId()), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/place-order/table/{tableNumber}")
    public ResponseEntity<?> placeOrder(@RequestBody Integer tableNumber){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        RestaurantDetails restaurant = restaurantUser.getRestDetails();
        Long restaurantId = restaurant.getRestId();
        CustomerInfoDTO customer = new CustomerInfoDTO();
        customer.setCustomerId(restaurantId);
        customer.setEmailId(restaurant.getEmail());
        customer.setFirstName(restaurant.getOwnerName().split("\\s+")[0]);
        customer.setLastName(restaurant.getOwnerName().split("\\s+")[1]);
        customer.setPhoneNumber(restaurant.getPhone());
        
        ReservationDetailsDto reservation = new ReservationDetailsDto();
        reservation.setCustomerInfo(customer);
        reservation.setDate(LocalDate.now());
        reservation.setNumberOfSeats(1);
        reservation.setTableNumber(tableNumber);
        String startTime = String.valueOf(LocalDateTime.now()).substring(11,16);
        String endTime = String.valueOf(LocalDateTime.now().plusHours(2)).substring(11,16);
        reservation.setTs(new TimeSpan(startTime, endTime));
        
        return new ResponseEntity<>(restOrderService.placeOrder(reservation), HttpStatus.ACCEPTED);
    }
    
}
