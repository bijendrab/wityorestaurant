package com.wityorestaurant.modules.orderservice.controller;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import com.wityorestaurant.modules.orderservice.dto.RestaurantOrderDTO;
import com.wityorestaurant.modules.orderservice.dto.UpdateOrderItemDTO;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderHistory;
import com.wityorestaurant.modules.orderservice.model.OrderItem;
import com.wityorestaurant.modules.orderservice.service.OrderService;
import com.wityorestaurant.modules.orderservice.service.RestaurantOrderService;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
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

    @Autowired
    RestaurantOrderService restOrderService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getAllTableOrder")
    public ResponseEntity<?> getAllTableOrder() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<>(orderService.getAllTableOrderDetails(restaurantUser.getRestDetails().getRestId()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/order-by-table/{tableId}")
    public ResponseEntity<?> getOrderByTable(@PathVariable Long tableId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<>(orderService.getRestaurantTableOrders(tableId, restaurantUser.getRestDetails().getRestId()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/place-order/table/{tableId}")
    public ResponseEntity<?> placeOrder(@PathVariable Long tableId, @RequestBody RestaurantOrderDTO orderDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        RestaurantDetails restaurant = restaurantUser.getRestDetails();
        return new ResponseEntity<>(restOrderService.placeOrder(orderDTO, tableId, restaurant), HttpStatus.ACCEPTED);
    }

    @PutMapping("/delete-ordered-item/{orderId}")
    public ResponseEntity<?> deleteOrderItemFromOrder(@RequestBody UpdateOrderItemDTO dto,
                                                      @PathVariable String orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<Boolean>(restOrderService.removePlacedOrderItem(dto, restaurantUser.getRestDetails().getRestId(), orderId), HttpStatus.OK);
    }

    @PutMapping("/update-ordered-item/{orderId}")
    public ResponseEntity<?> updateOrderItemFromOrder(@RequestBody UpdateOrderItemDTO dto, @PathVariable String orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<Order>(restOrderService.updateOrderedItem(dto, restaurantUser.getRestDetails().getRestId(), orderId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update-orderitem-charges/{orderId}")
    public ResponseEntity<?> updateOrderItemFromOrder(@RequestBody OrderItem orderItem, @PathVariable String orderId) {
        return new ResponseEntity<OrderItem>(restOrderService.updateOrderItemSpecialDiscount(orderItem, orderId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/save-order-history/{tableId}")
    public ResponseEntity<Boolean> navigateOrderHistory(@PathVariable("tableId") Long tableId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<>(restOrderService.saveToOrderHistory(restaurantUser.getRestDetails().getRestId(), tableId),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-order-history-all-tables")
    public ResponseEntity<List<OrderHistory>> getOrderAllTableHistory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<>(restOrderService.getOrderHistory(restaurantUser.getRestDetails().getRestId(), 0L,-1,"",""),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-order-history-all-tables/{startDateTIme}/{endDateTIme}")
    public ResponseEntity<List<OrderHistory>> getOrderAllTableStartEndHistory(@PathVariable("startDateTIme") String startDateTIme,
                                                                      @PathVariable("endDateTIme") String endDateTIme) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<>(restOrderService.getOrderHistory(restaurantUser.getRestDetails().getRestId(), 0L,-1,startDateTIme,endDateTIme),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-order-history-all-tables/{startDateTIme}")
    public ResponseEntity<List<OrderHistory>> getOrderAllTableStartHistory(@PathVariable("startDateTIme") String startDateTIme) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<>(restOrderService.getOrderHistory(restaurantUser.getRestDetails().getRestId(), 0L,-1,startDateTIme,""),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-order-history-table/{tableId}")
    public ResponseEntity<List<OrderHistory>> getOrderTableHistory(@PathVariable("tableId") Long tableId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<>(restOrderService.getOrderHistory(restaurantUser.getRestDetails().getRestId(), tableId,0,"",""),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-order-history-table/{tableId}/{startDateTIme}/{endDateTIme}")
    public ResponseEntity<List<OrderHistory>> getOrderTableStartEndHistory(@PathVariable("tableId") Long tableId,
                                                                      @PathVariable("startDateTIme") String startDateTIme,
                                                                      @PathVariable("endDateTIme") String endDateTIme) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<>(restOrderService.getOrderHistory(restaurantUser.getRestDetails().getRestId(), tableId,0,startDateTIme,endDateTIme),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-order-history-table/{tableId}/{startDateTIme}")
    public ResponseEntity<List<OrderHistory>> getOrderTableStartHistory(@PathVariable("tableId") Long tableId,
                                                                      @PathVariable("startDateTIme") String startDateTIme) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<>(restOrderService.getOrderHistory(restaurantUser.getRestDetails().getRestId(), tableId,0,startDateTIme,""),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-order-history-table-duration/{tableId}/{duration}")
    public ResponseEntity<List<OrderHistory>> getOrderDurationHistory(@PathVariable("tableId") Long tableId,
                                                              @PathVariable("duration") @NotBlank @Min(1) int duration ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restaurantUser = restaurantUserRepository.findByUsername(auth.getName());
        return new ResponseEntity<>(restOrderService.getOrderHistory(restaurantUser.getRestDetails().getRestId(), tableId,duration,"",""),HttpStatus.OK);
    }

}
