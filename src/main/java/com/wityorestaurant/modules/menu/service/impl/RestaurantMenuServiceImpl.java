package com.wityorestaurant.modules.menu.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.model.ProductQuantityOptions;
import com.wityorestaurant.modules.menu.repository.MenuRepository;
import com.wityorestaurant.modules.menu.service.RestaurantMenuService;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;

@Service(value = "RestaurantMenuService")
public class RestaurantMenuServiceImpl implements RestaurantMenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RestaurantUserRepository userRepository;


    public List<Product> getAllProducts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
        return tempUser.getRestDetails().getProduct();
    }

    public Product getMenuItemById(String productId) {
        Product product = getMenuItem(productId);
        return product;
    }

    private Product getMenuItem(String productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
        return menuRepository.findByItemAndRestId(productId,tempUser.getRestDetails().getRestId());
    }

    @Transactional
    public String deleteMenuItem(String productId) {
        try {
            menuRepository.delete(getMenuItem(productId));
            return "menu item deleted";
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String setMenuItemStatus(String productId){
        Product product = getMenuItem(productId);
        if(product.getIsEnabled().equals(true)){
            product.setIsEnabled(false);
            menuRepository.save(product);
            return "Item Disabled";
        }
        else{
            product.setIsEnabled(true);
            menuRepository.save(product);
            return "Item is Enabled";
        }
    }


    public Product addMenuItem(Product product) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
        String productUUID = UUID.randomUUID().toString();
        productUUID = productUUID.replaceAll("-", "");
        product.setRestaurantDetails(tempUser.getRestDetails());
        product.setProductId(productUUID);
        setMenu(product);
        return menuRepository.save(product);
    }

    public Product editMenuItem(Product product) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
        product.setRestaurantDetails(tempUser.getRestDetails());
        setMenu(product);
        return menuRepository.save(product);
    }

   public void setMenu(Product product) {
        Set<ProductQuantityOptions> pqo = new HashSet<ProductQuantityOptions>();
        for (ProductQuantityOptions potion : product.getQuantityOption()) {
            potion.setProduct(product);
            pqo.add(potion);
        }
    }

	public List<Product> getMenuByRestaurantId(Long restId) {
		return menuRepository.findByRestaurantId(restId);
	}
}