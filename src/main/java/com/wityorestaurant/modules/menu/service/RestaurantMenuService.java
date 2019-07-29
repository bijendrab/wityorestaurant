package com.wityorestaurant.modules.menu.service;

import java.util.List;

import com.wityorestaurant.modules.menu.dto.RestaurantMenuDto;
import com.wityorestaurant.modules.menu.model.Product;

public interface RestaurantMenuService {
    List<Product> getAllProducts();

    Product getMenuItemById(String productId);

    String deleteMenuItem(String productId);

    Product addMenuItem(Product product);

    Product editMenuItem(Product product);

    String setMenuItemStatus(String productId);
    
    RestaurantMenuDto getMenuByRestaurantId(Long restId);
}


