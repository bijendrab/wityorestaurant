package com.wityorestaurant.modules.cart.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.cart.model.RestaurantCartAddOnItemChange;
import com.wityorestaurant.modules.cart.model.RestaurantCartAddOnItems;
import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import com.wityorestaurant.modules.cart.model.RestaurantCartItemInput;
import com.wityorestaurant.modules.cart.model.RestaurantCartItemQuanityChange;
import com.wityorestaurant.modules.cart.repository.CartItemRepository;
import com.wityorestaurant.modules.cart.service.CartService;
import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.repository.MenuRepository;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private RestaurantUserRepository userRepository;
    @Autowired
    private RestTableRepository tableRepository;

    @Autowired
    private MenuRepository menuRepository;

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


    public String addToCart(RestaurantCartItemInput restaurantCartItemInput) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
            RestaurantDetails restaurant = tempUser.getRestDetails();

            RestaurantCart cart = restaurant.getCart();
            List<RestaurantCartItem> restaurantCartItems = cart.getCartItems().stream().filter
                (item -> item.getTable().getId() == restaurantCartItemInput.getTableId()).collect(Collectors.toList());
            String productId = restaurantCartItemInput.getProductId();
            Product product = menuRepository.findByItemAndRestId(productId, restaurant.getRestId());
            for (RestaurantCartItem cartItem : restaurantCartItems) {
                String productJson = cartItem.getProductJson();
                Product p = menuRepository.findByItemAndRestId(productJson, restaurant.getRestId());
                if ((productId.equalsIgnoreCase(p.getProductId()) && cartItem.getQuantityOption().equals(restaurantCartItemInput.getRestaurantSelectQuantityOption()))) {
                    if (compareAddOn(cartItem, restaurantCartItemInput)) {
                        double newPrice = cartItem.getPrice() / cartItem.getQuantity();
                        cartItem.setQuantity(cartItem.getQuantity() + restaurantCartItemInput.getRestaurantSelectQuantityAmount());
                        cartItem.setPrice(cartItem.getQuantity() * newPrice);
                        cartItem.setUpdateItemInCartTime(LocalDateTime.now());
                        cartItemRepository.save(cartItem);
                        return "Item Incremented in Cart";
                    }
                }
            }
            RestaurantCartItem newCartItem = new RestaurantCartItem();
            newCartItem.setCart(cart);
            newCartItem.setItemName(product.getProductName());
            newCartItem.setQuantity(restaurantCartItemInput.getRestaurantSelectQuantityAmount());
            newCartItem.setProductJson(productId);
            newCartItem.setOrderTaker(restaurantCartItemInput.getOrderTaker());
            newCartItem.setTable(tableRepository.findById(restaurantCartItemInput.getTableId()).get());
            product.getProductQuantityOptions().forEach(qOption2 -> {
                if (qOption2.getQuantityOption().equalsIgnoreCase(restaurantCartItemInput.getRestaurantSelectQuantityOption())) {
                    newCartItem.setPrice(qOption2.getPrice() * newCartItem.getQuantity());
                    newCartItem.setQuantityOption(restaurantCartItemInput.getRestaurantSelectQuantityOption());
                }
            });
            newCartItem.setRestaurantCartAddOnItems(convertListToSet(restaurantCartItemInput.getRestaurantCartAddOnItemsList()));
            for (RestaurantCartAddOnItems selectCartAddOnItems : newCartItem.getRestaurantCartAddOnItems()) {
                selectCartAddOnItems.setRestaurantCartItem(newCartItem);
            }
            newCartItem.setAddItemToCartTime(LocalDateTime.now());
            newCartItem.setUpdateItemInCartTime(LocalDateTime.now());
            cartItemRepository.save(newCartItem);
            return "Item Added to Cart";

        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public String updateQuantityItemInCart(RestaurantCartItemQuanityChange restaurantCartItemQuanityChange) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
            RestaurantDetails restaurant = tempUser.getRestDetails();

            RestaurantCart cart = restaurant.getCart();
            List<RestaurantCartItem> restaurantCartItems = cart.getCartItems().stream().filter
                (item -> item.getCartItemId() == restaurantCartItemQuanityChange.getCartItemId()).collect(Collectors.toList());
            RestaurantCartItem tempCartItem = restaurantCartItems.get(0);
            double newPrice= tempCartItem.getPrice()/tempCartItem.getQuantity();
            tempCartItem.setQuantity(restaurantCartItemQuanityChange.getRestaurantSelectQuantityAmount());
            tempCartItem.setPrice(tempCartItem.getQuantity() * newPrice);
            cartItemRepository.save(tempCartItem);
            return "Cart Item Quantity Updated";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String updateAddOnItemsInCart(RestaurantCartAddOnItemChange restaurantCartAddOnItemChange) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
            RestaurantDetails restaurant = tempUser.getRestDetails();
            RestaurantCart cart = restaurant.getCart();
            List<RestaurantCartItem> restaurantCartItems = cart.getCartItems().stream().filter
                (item -> item.getCartItemId() == restaurantCartAddOnItemChange.getCartItemId()).collect(Collectors.toList());
            RestaurantCartItem tempCartItem = restaurantCartItems.get(0);
            restaurantCartAddOnItemChange.getRestaurantCartAddOnItemsList().forEach(item -> {
                item.setRestaurantCartItem(tempCartItem);
            });
            tempCartItem.getRestaurantCartAddOnItems().clear();
            tempCartItem.getRestaurantCartAddOnItems().addAll(restaurantCartAddOnItemChange.getRestaurantCartAddOnItemsList());
            cartItemRepository.save(tempCartItem);
            return "Add on Items in Cart Updated";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String deleteCartItemById(Long cartItemId) {
        try {
            cartItemRepository.deleteById(cartItemId);
            return "cart item deleted";
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    private boolean compareAddOn(RestaurantCartItem restaurantCartItem, RestaurantCartItemInput restaurantCartItemInput) {
        try {
            ObjectMapper Obj = new ObjectMapper();
            ArrayList<String> selectUserCartAddOnItemsArrayList = new ArrayList<>();
            ArrayList<String> selectCartAddOnItemsArrayList = new ArrayList<>();
            for (int i = 0; i < restaurantCartItemInput.getRestaurantCartAddOnItemsList().size(); i++) {
                String jsonStr = Obj.writeValueAsString(restaurantCartItemInput.getRestaurantCartAddOnItemsList().get(i));
                JSONObject jSONObject = new JSONObject(jsonStr);
                jSONObject.remove("cartAddOnId");
                jSONObject.remove("cartItem");
                selectUserCartAddOnItemsArrayList.add(jSONObject.toString());
            }
            for (RestaurantCartAddOnItems selectCartAddOnItems:restaurantCartItem.getRestaurantCartAddOnItems()) {
                String jsonStr = Obj.writeValueAsString(selectCartAddOnItems);
                JSONObject jSONObject = new JSONObject(jsonStr);
                jSONObject.remove("cartAddOnId");
                jSONObject.remove("cartItem");
                selectCartAddOnItemsArrayList.add(jSONObject.toString());
            }

            if(restaurantCartItem.getRestaurantCartAddOnItems().size() >= restaurantCartItemInput.getRestaurantCartAddOnItemsList().size()) {
                for (RestaurantCartAddOnItems selectCartAddOnItems : restaurantCartItem.getRestaurantCartAddOnItems()) {
                    String jsonStr = Obj.writeValueAsString(selectCartAddOnItems);
                    JSONObject jSONObject = new JSONObject(jsonStr);
                    jSONObject.remove("cartAddOnId");
                    jSONObject.remove("cartItem");
                    if (selectUserCartAddOnItemsArrayList.contains(jSONObject.toString())) {
                        continue;
                    } else {
                        return false;
                    }

                }
            }
            else{
                for (RestaurantCartAddOnItems selectCartAddOnItems : restaurantCartItem.getRestaurantCartAddOnItems()) {
                    String jsonStr = Obj.writeValueAsString(selectCartAddOnItems);
                    JSONObject jSONObject = new JSONObject(jsonStr);
                    jSONObject.remove("cartAddOnId");
                    jSONObject.remove("cartItem");
                    if (selectCartAddOnItemsArrayList.contains(jSONObject.toString())) {
                        continue;
                    } else {
                        return false;
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static <T> Set<T> convertListToSet(List<T> list) {
        return list.stream().collect(Collectors.toSet());
    }
}
