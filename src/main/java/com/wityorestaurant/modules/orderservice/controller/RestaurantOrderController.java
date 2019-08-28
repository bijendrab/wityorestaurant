package com.wityorestaurant.modules.orderservice.controller;

import com.wityorestaurant.modules.orderservice.service.OrderService;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/restaurantOrder")
public class RestaurantOrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    RestaurantUserRepository restaurantUserRepository;

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
    
}
