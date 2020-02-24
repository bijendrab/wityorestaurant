package com.wityorestaurant.modules.cart.service.impl;

import com.google.gson.Gson;
import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import com.wityorestaurant.modules.cart.repository.CartItemRepository;
import com.wityorestaurant.modules.cart.service.CartService;
import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.model.ProductQuantityOptions;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private RestaurantUserRepository userRepository;
    @Autowired
    private RestTableRepository tableRepository;

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    public RestaurantCart getCart(Long tableId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
            RestaurantDetails restaurant = tempUser.getRestDetails();
            RestaurantCart cart = restaurant.getCart();
            cart.setCartItems(cart.getCartItems().stream().filter(item -> item.getTable().getId() == tableId).collect(Collectors.toList()));
            return cart;
        } catch (Exception e) {
        }
        return null;
    }

    public RestaurantCartItem addCart(Product product, String quantityOption, Long tableId, String orderTaker) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
            RestaurantDetails restaurant = tempUser.getRestDetails();

            RestaurantCart cart = restaurant.getCart();
            List<RestaurantCartItem> userCartItems = cart.getCartItems();
            String productId = product.getProductId();
            RestaurantCartItem tempCartItem = null;
            for (RestaurantCartItem cartItem : userCartItems) {
                Product p = new Gson().fromJson(cartItem.getProductJson(), Product.class);
                if (productId.equalsIgnoreCase(p.getProductId()) && cartItem.getQuantityOption().equals(quantityOption)) {
                    tempCartItem = cartItem;
                    break;
                }
            }

            if (tempCartItem != null) {
                    if (tempCartItem.getQuantityOption().equalsIgnoreCase(quantityOption)) {
                        double newPrice= tempCartItem.getPrice()/tempCartItem.getQuantity();
                        tempCartItem.setQuantity(tempCartItem.getQuantity() + Integer.parseInt(product.getSelectedQuantity()));
                        tempCartItem.setPrice(tempCartItem.getQuantity() * newPrice);
                        tempCartItem.setQuantityOption(tempCartItem.getQuantityOption());
                        cart.setTotalPrice(cart.getTotalPrice() + tempCartItem.getPrice());
                        return cartItemRepository.save(tempCartItem);
                    }
            }
            RestaurantCartItem newCartItem = new RestaurantCartItem();
            newCartItem.setCart(cart);
            newCartItem.setItemName(product.getProductName());
            newCartItem.setQuantity(Integer.parseInt(product.getSelectedQuantity()));
            newCartItem.setProductJson(new Gson().toJson(product));
            newCartItem.setOrderTaker(orderTaker);
            newCartItem.setTable(tableRepository.findById(tableId).get());
            product.getProductQuantityOptions().forEach(qOption -> {
                if (qOption.getQuantityOption().equalsIgnoreCase(quantityOption)) {
                    newCartItem.setPrice(qOption.getPrice() * newCartItem.getQuantity());
                    newCartItem.setQuantityOption(quantityOption);
                }
            });
            return cartItemRepository.save(newCartItem);
        } catch (Exception e) {
            logger.debug("Error in add to cart {}", e);
        }
        return null;
    }

    public RestaurantCartItem updateCart(Product product, String quantityOption, Long tableId, String orderTaker) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
            RestaurantDetails restaurant = tempUser.getRestDetails();

            RestaurantCart cart = restaurant.getCart();
            List<RestaurantCartItem> userCartItems = cart.getCartItems();
            String productId = product.getProductId();
            RestaurantCartItem tempCartItem = null;
            for (RestaurantCartItem cartItem : userCartItems) {
                Product p = new Gson().fromJson(cartItem.getProductJson(), Product.class);
                if ((productId.equalsIgnoreCase(p.getProductId()) && cartItem.getQuantityOption().equals(quantityOption)) ||
                    (productId.equalsIgnoreCase(p.getProductId()) && !cartItem.getQuantityOption().equals(quantityOption))) {
                    tempCartItem = cartItem;
                    break;
                }
            }

            if (tempCartItem != null) {
                    double newPrice= tempCartItem.getPrice()/tempCartItem.getQuantity();
                    tempCartItem.setQuantity(Integer.parseInt(product.getSelectedQuantity()));
                    tempCartItem.setPrice(tempCartItem.getQuantity() * newPrice);
                    tempCartItem.setQuantityOption(quantityOption);
                    cart.setTotalPrice(tempCartItem.getQuantity() * newPrice);
                    return cartItemRepository.save(tempCartItem);

            }

        } catch (Exception e) {
            logger.debug("Error in add to cart {}", e);
        }
        return null;
    }

    public String deleteCartItemById(Long cartItemId) {
        try {
            cartItemRepository.deleteById(cartItemId);
            return "deleted";
        } catch (Exception e) {
        }
        return "failure";
    }

    public String reduceCartItem(String productId, String quantityOption) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
            RestaurantDetails restaurant = tempUser.getRestDetails();

            RestaurantCart cart = restaurant.getCart();

            for (RestaurantCartItem cartItem : cart.getCartItems()) {
                Product product = new Gson().fromJson(cartItem.getProductJson(), Product.class);
                if (productId.equals(product.getProductId())) {
                    int updatedQuantity = cartItem.getQuantity() - 1;
                    if (updatedQuantity == 0) {
                        cartItemRepository.deleteById(cartItem.getCartItemId());
                        return "cart updated";
                    }
                    cartItem.setQuantity(updatedQuantity);
                    for (ProductQuantityOptions qOption : product.getProductQuantityOptions()) {
                        if (cartItem.getQuantityOption().equalsIgnoreCase(quantityOption)) {
                            cartItem.setPrice(updatedQuantity * qOption.getPrice());
                            cartItemRepository.save(cartItem);
                            return "cart updated";
                        }
                    }
                }
            }
            return "operation failed";
        } catch (Exception e) {
        }
        return "operation failed";
    }
}
