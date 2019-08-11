package com.wityorestaurant.modules.orderservice.controller;

import com.wityorestaurant.modules.customerdata.CustomerOrderDTO;
import com.wityorestaurant.modules.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/checkout/{restaurantId}")
    public ResponseEntity<?> createOrder(@PathVariable("restaurantId") Long restId, @RequestBody CustomerOrderDTO customerCheckoutItems){
        return new ResponseEntity<>(orderService.processOrderRequest(customerCheckoutItems,restId), HttpStatus.ACCEPTED);
    }
}
