package com.wityorestaurant.modules.menu.service.impl;

import com.wityorestaurant.modules.config.model.Category;
import com.wityorestaurant.modules.config.repository.CategoryRepository;
import com.wityorestaurant.modules.menu.dto.RestaurantMenuDto;
import com.wityorestaurant.modules.menu.model.AddOnProfile;
import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.model.ProductQuantityOptions;
import com.wityorestaurant.modules.menu.repository.AddOnRepository;
import com.wityorestaurant.modules.menu.repository.MenuRepository;
import com.wityorestaurant.modules.menu.service.RestaurantMenuService;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service(value = "RestaurantMenuService")
public class RestaurantMenuServiceImpl implements RestaurantMenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RestaurantUserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AddOnRepository addOnRepository;

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
        return menuRepository.findByItemAndRestId(productId, tempUser.getRestDetails().getRestId());
    }

    @Transactional
    public String deleteMenuItem(String productId) {
        try {
            menuRepository.delete(getMenuItem(productId));
            return "menu item deleted";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String setMenuItemStatus(String productId) {
        Product product = getMenuItem(productId);
        if (product.getIsEnabled().equals(true)) {
            product.setIsEnabled(false);
            menuRepository.save(product);
            return "Item Disabled";
        } else {
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
        List<Category> categories = categoryRepository.getCategoryByRestaurantId(tempUser.getRestDetails().getRestId());
        Category categoryObj = categories.stream().filter(category -> category.getCategoryName().equals(product.getCategory().getCategoryName())).findFirst().get();
        product.setCategory(categoryObj);
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
        for (ProductQuantityOptions potion : product.getProductQuantityOptions()) {
            potion.setProduct(product);
            pqo.add(potion);
        }
    }

    public RestaurantMenuDto getMenuByRestaurantId(Long restId) {
        RestaurantMenuDto dto = new RestaurantMenuDto();
        List<Product> menuList = menuRepository.findByRestaurantId(restId);
        dto.setRestaurantMenu(menuList);
        return dto;
    }

    private Product getMenuItemForCustomer(String productId, Long restId) {
        return menuRepository.findByItemAndRestId(productId, restId);
    }

    public Product getMenuItemByIdCustomer(Long restId, String productId) {
        Product product = getMenuItemForCustomer(productId,restId);
        return product;
    }
}
