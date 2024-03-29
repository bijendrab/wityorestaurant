package com.wityorestaurant.modules.cart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.wityorestaurant.common.Constant;
import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.cart.model.RestaurantCartAddOnItemChange;
import com.wityorestaurant.modules.cart.model.RestaurantCartAddOnItems;
import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import com.wityorestaurant.modules.cart.model.RestaurantCartItemInput;
import com.wityorestaurant.modules.cart.model.RestaurantCartItemQuanityChange;
import com.wityorestaurant.modules.cart.service.CartService;
import com.wityorestaurant.modules.menu.model.AddOnItems;
import com.wityorestaurant.modules.menu.model.Product;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "get cart of a Restaurant", response = RestaurantCart.class)
    @GetMapping("/get-cart/{tableId}")
    public ResponseEntity<?> getCart(@PathVariable Long tableId) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Get Cart Items of a Table");
        response.put("body", cartServiceImpl.getCart(tableId));
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/addToCart")
    public ResponseEntity<?> addCartItem(@RequestBody RestaurantCartItemInput restaurantCartItemInput) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Adding Item to Cart");
        String addToCartStatus = cartServiceImpl.addToCart(restaurantCartItemInput);
        if(addToCartStatus == null){
            response.put("body","Item did not get added to Cart. Check the http method/request input again please");
            response.put("error", true);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("body", addToCartStatus);
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateQuantity")
    public ResponseEntity<?>  UpdateCartItemQuantity(@RequestBody RestaurantCartItemQuanityChange restaurantCartItemQuanityChange) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Update in Cart Item Quantity");
        String updateQuantityInCartStatus = cartServiceImpl.updateQuantityItemInCart(restaurantCartItemQuanityChange);
        if(updateQuantityInCartStatus == null){
            response.put("body","Item Quantity did not get changed. Check the http method/request input again please");
            response.put("error", true);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("body", updateQuantityInCartStatus);
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateAddOn")
    public ResponseEntity<?>  UpdateCartAddOn(@RequestBody RestaurantCartAddOnItemChange restaurantCartAddOnItemChange) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Update in Cart Item AddOn");
        String updateAddOnInCartStatus = cartServiceImpl.updateAddOnItemsInCart(restaurantCartAddOnItemChange);
        if(updateAddOnInCartStatus == null){
            response.put("body","Add On did not get updated. Check the http method/request input again please");
            response.put("error", true);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("body", updateAddOnInCartStatus);
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long cartItemId) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Delete Cart Item");
        String deleteCartITemStatus = cartServiceImpl.deleteCartItemById(cartItemId);
        if(deleteCartITemStatus == null){
            response.put("body","Delete CartItem did not happen. Check the http method/request input again please");
            response.put("error", true);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("body",deleteCartITemStatus );
        response.put("status", HttpStatus.ACCEPTED);
        response.put("error", false);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
