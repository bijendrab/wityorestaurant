package com.wityorestaurant.modules.menu.controller;

import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.service.RestaurantMenuService;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import com.wityorestaurant.modules.restaurant.service.RestaurantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    RestaurantUserService restUserServiceImpl;

    @Autowired
    RestaurantMenuService restMenuServiceImpl;

    @Autowired
    AuthenticationManager authManager;


    @Autowired
    RestaurantUserRepository userRepo;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/addMenuItem")
    public ResponseEntity<?> addMenuItem(@RequestBody Product product) {
        return new ResponseEntity<>(restMenuServiceImpl.addMenuItem(product), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getMenu")
    public ResponseEntity<?> getMenu() {
        return new ResponseEntity<>(restMenuServiceImpl.getAllProducts(), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getMenu/{itemId}")
    public ResponseEntity<?> getMenuItem(@PathVariable(value = "itemId") String itemId) {
        return new ResponseEntity<>(restMenuServiceImpl.getMenuItemById(itemId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/editMenuItem")
    public ResponseEntity<?> editMenuItem(@RequestBody Product product) {
        return new ResponseEntity<>(restMenuServiceImpl.editMenuItem(product), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/deleteMenuItem/{itemId}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable(value = "itemId") String itemId) {
        return new ResponseEntity<>(restMenuServiceImpl.deleteMenuItem(itemId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/setMenuItemStatus/{itemId}")
    public ResponseEntity<?> setMenuItemStatus(@PathVariable(value = "itemId") String itemId) {
        return new ResponseEntity<>(restMenuServiceImpl.setMenuItemStatus(itemId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<?> getMenuItemByRestaurantId(@PathVariable("restaurantId") Long restId) {
        return new ResponseEntity<>(restMenuServiceImpl.getMenuByRestaurantId(restId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-restaurant-order-menu")
    public ResponseEntity<?> getMenuItemForRestaurantOrders() {
        return new ResponseEntity<List<Product>>(restMenuServiceImpl.getAllProducts(), HttpStatus.OK);
    }
}
