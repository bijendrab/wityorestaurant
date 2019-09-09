package com.wityorestaurant.modules.orderservice.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wityorestaurant.modules.orderservice.dto.RestaurantOrderDTO;
import com.wityorestaurant.modules.orderservice.dto.UpdateOrderItemDTO;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderItem;
import com.wityorestaurant.modules.orderservice.service.OrderService;
import com.wityorestaurant.modules.orderservice.service.RestaurantOrderService;
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
    @PostMapping("/place-order/table/{tableId}")
    public ResponseEntity<?> placeOrder(@PathVariable Long tableId, @RequestBody RestaurantOrderDTO orderDTO){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        RestaurantDetails restaurant = restaurantUser.getRestDetails();
        return new ResponseEntity<>(restOrderService.placeOrder(orderDTO, tableId, restaurant), HttpStatus.ACCEPTED);
    }
    
    @PutMapping("/delete-ordered-item/{orderId}")
    public ResponseEntity<?> deleteOrderItemFromOrder(@RequestBody UpdateOrderItemDTO dto,
    		@PathVariable Long orderId,
    		@RequestParam Long tableId){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
    	return new ResponseEntity<Boolean>(restOrderService.removePlacedOrderItem(dto, restaurantUser.getRestDetails().getRestId(), orderId, tableId), HttpStatus.OK);
    }
    
    @PutMapping("/update-ordered-item/{orderId}")
    public ResponseEntity<?> updateOrderItemFromOrder(@RequestBody UpdateOrderItemDTO dto, @PathVariable Long orderId){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
    	return new ResponseEntity<Order>(restOrderService.updateOrderedItem(dto, restaurantUser.getRestDetails().getRestId(), orderId), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update-orderitem-charges/{orderId}")
    public ResponseEntity<?> updateOrderItemFromOrder(@RequestBody OrderItem orderItem, @PathVariable Long orderId){
        return new ResponseEntity<OrderItem>(restOrderService.updateOrderItemSpecialDiscount(orderItem, orderId),HttpStatus.OK);
    }
     
    
}
