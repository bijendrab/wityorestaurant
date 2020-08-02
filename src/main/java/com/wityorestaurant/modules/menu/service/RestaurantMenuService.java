package com.wityorestaurant.modules.menu.service;

import com.wityorestaurant.modules.menu.dto.RestaurantMenuDto;
import com.wityorestaurant.modules.menu.model.Product;

import java.util.List;

public interface RestaurantMenuService {
    List<Product> getAllProducts();

    Product getMenuItemById(String productId);

    String deleteMenuItem(String productId);

    Product addMenuItem(Product product);

    Product editMenuItem(Product product);

    String setMenuItemStatus(String productId);

    RestaurantMenuDto getMenuByRestaurantId(Long restId);

    Product getMenuItemByIdCustomer(Long restId, String productId);
}


