package com.wityorestaurant.modules.cart.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import com.wityorestaurant.modules.cart.repository.CartItemRepository;
import com.wityorestaurant.modules.cart.repository.CartRepository;
import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.model.ProductQuantityOptions;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;

@Service
public class CartServiceImpl {
	
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
    private RestaurantUserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;
	
	public RestaurantCart getCart() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
	        RestaurantDetails restaurant = tempUser.getRestDetails();
	        RestaurantCart cart = restaurant.getCart();
	        return cart;
		} catch (Exception e) {
		}
		return null;
	}

	public String addOrUpdateCart(Product product, String quantityOption) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
	        RestaurantDetails restaurant = tempUser.getRestDetails();
	                
	        RestaurantCart cart = restaurant.getCart();
	        List<RestaurantCartItem> userCartItems = cart.getCartItems();
			String productId = product.getProductId();
			RestaurantCartItem tempCartItem = null;
			for(RestaurantCartItem cartItem : userCartItems) {
				Product p = cartItem.getProduct();
				if(productId.equalsIgnoreCase(p.getProductId())) {
					tempCartItem = cartItem;
					break;
				}
			}
			
			if (tempCartItem != null) {
				Product tempProduct = tempCartItem.getProduct();
				Set<ProductQuantityOptions> productQuantityOptions = tempProduct.getProductQuantityOptions();
				for (ProductQuantityOptions qOption : productQuantityOptions) {
					if (qOption.getQuantityOption().equalsIgnoreCase(quantityOption)
							&& tempCartItem.getQuantityOption().equalsIgnoreCase(quantityOption)) {
						tempCartItem.setQuantity(tempCartItem.getQuantity() + 1);
						tempCartItem.setPrice(tempCartItem.getQuantity() * qOption.getPrice());
						cartItemRepository.save(tempCartItem);
						break;
					}
					if (qOption.getQuantityOption().equalsIgnoreCase(quantityOption)
							&& !tempCartItem.getQuantityOption().equalsIgnoreCase(quantityOption)) {
						tempCartItem.setPrice(tempCartItem.getQuantity() * qOption.getPrice());
						tempCartItem.setQuantityOption(qOption.getQuantityOption());
						cartItemRepository.save(tempCartItem);
						break;
					}
				}
				return "updated";
			}
			RestaurantCartItem newCartItem = new RestaurantCartItem();
			newCartItem.setCart(cart);
			newCartItem.setItemName(product.getProductName());
			newCartItem.setQuantity(1);
			newCartItem.setProduct(product);
			product.getProductQuantityOptions().forEach(qOption -> {
				if (qOption.getQuantityOption().equalsIgnoreCase(quantityOption)) {
					newCartItem.setPrice(qOption.getPrice() * 1);
					newCartItem.setQuantityOption(quantityOption);
				}
			});
			cartItemRepository.save(newCartItem);
			return "added";
		} catch (Exception e) {
			
		}
		return "error";    
	}

	public String deleteCartItemById(Long cartItemId) {
		try {
			cartItemRepository.deleteById(cartItemId);
			return "deleted";
		} catch (Exception e) {
		}
		return "failure";
	}

	public String reduceCartItem(String productId, String quantityOption){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
	        RestaurantDetails restaurant = tempUser.getRestDetails();
	                
	        RestaurantCart cart = restaurant.getCart();
			
			for(RestaurantCartItem cartItem : cart.getCartItems()) {
				Product product = cartItem.getProduct();
				if(productId.equals(product.getProductId())) {
					int updatedQuantity = cartItem.getQuantity() - 1;
					if(updatedQuantity == 0) {
						cartItemRepository.deleteById(cartItem.getCartItemId());
						return "cart updated";
					}
					cartItem.setQuantity(updatedQuantity);
					for(ProductQuantityOptions qOption : product.getProductQuantityOptions()) {
						if(cartItem.getQuantityOption().equalsIgnoreCase(quantityOption)) {
							cartItem.setPrice(updatedQuantity * qOption.getPrice());
							cartItemRepository.save(cartItem);
							return "cart updated";
						}
					}
				}
			}
			return "operation failed";
		} catch (Exception e) {	}
		return "operation failed";
	}
}
