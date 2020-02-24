package com.wityorestaurant.modules.cart.controller;

import com.wityorestaurant.common.Constant;
import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import com.wityorestaurant.modules.cart.service.CartService;
import com.wityorestaurant.modules.menu.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(Constant.RESTAURANT_CART_API)
public class CartController {

    @Autowired
    private CartService cartServiceImpl;

    @GetMapping("/get-cart/{tableId}")
    public ResponseEntity<?> getCart(@PathVariable Long tableId) {
        return new ResponseEntity<RestaurantCart>(cartServiceImpl.getCart(tableId), HttpStatus.OK);
    }

    @PostMapping("/add/{quantityOption}")
    public ResponseEntity<?> addCartItem(@RequestBody Product product,
                                                 @PathVariable String quantityOption,
                                                 @RequestParam Long tableId,
                                                 @RequestParam String immediateStatus,
                                                 @RequestParam String orderTaker
    ) {
        return new ResponseEntity<RestaurantCartItem>(cartServiceImpl.addCart(product, quantityOption, tableId, orderTaker), HttpStatus.OK);
    }

    @PutMapping("/update/{quantityOption}")
    public ResponseEntity<?>  UpdateCartItem(@RequestBody Product product,
                                                 @PathVariable String quantityOption,
                                                 @RequestParam Long tableId,
                                                 @RequestParam String immediateStatus,
                                                 @RequestParam String orderTaker
    ) {
        return new ResponseEntity<RestaurantCartItem>(cartServiceImpl.updateCart(product, quantityOption, tableId, orderTaker), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long cartItemId) {
        return new ResponseEntity<String>(cartServiceImpl.deleteCartItemById(cartItemId), HttpStatus.OK);
    }
}
