package com.wityorestaurant.modules.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wityorestaurant.common.Constant;
import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import com.wityorestaurant.modules.cart.service.CartService;
import com.wityorestaurant.modules.menu.model.Product;

@RestController
@RequestMapping(Constant.RESTAURANT_CART_API)
public class CartController {
	
	@Autowired
	private CartService cartServiceImpl;
	
	@GetMapping("/get-cart")
	public ResponseEntity<?> getCart(){
		return new ResponseEntity<RestaurantCart>(cartServiceImpl.getCart(), HttpStatus.OK);
	}
	
	@PostMapping("/add-update/{quantityOption}")
	public ResponseEntity<?> addOrUpdateCartItem(@RequestBody Product product,
			@PathVariable String quantityOption,
			@RequestParam Long tableId,
			@RequestParam String immediateStatus,
			@RequestParam String orderTaker
			){
		return new ResponseEntity<RestaurantCartItem>(cartServiceImpl.addOrUpdateCart(product, quantityOption, tableId, orderTaker), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{cartItemId}")
	public ResponseEntity<?> removeCartItem(@PathVariable Long cartItemId){
		return new ResponseEntity<String>(cartServiceImpl.deleteCartItemById(cartItemId), HttpStatus.OK);
	}
}
