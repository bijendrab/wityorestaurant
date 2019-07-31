package com.wityorestaurant.modules.config.service.impl;

import com.wityorestaurant.modules.config.dto.ConfigurationDTO;
import com.wityorestaurant.modules.config.dto.RestTableDTO;
import com.wityorestaurant.modules.config.model.*;
import com.wityorestaurant.modules.config.repository.*;
import com.wityorestaurant.modules.config.service.RestaurantConfigurationService;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service(value = "RestaurantConfigurationService")
public class RestaurantConfigurationServiceImpl implements RestaurantConfigurationService {
    @Autowired
    private RestTableRepository restTableRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CuisineRepository cuisineRepository;
    @Autowired
    private SubCategoryRepository subcategoryRepository;
    @Autowired
    private QuantityOptionRepository quantityoptionRepository;
    @Autowired
    private RestaurantUserRepository userRepository;

    public Object add(ConfigurationDTO config) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
        ConfigType configParams = ConfigType.valueOf(config.getType());
        switch (configParams){
            case Category:
                Category category = new Category();
                category.setCategoryName(config.getName());
                category.setCategoryType(config.getType());
                category.setSequence(config.getSequence());
                category.setRestaurantDetails(tempUser.getRestDetails());
                return categoryRepository.save(category);
            case SubCategory:
                SubCategory subcategory = new SubCategory();
                subcategory.setSubCategoryName(config.getName());
                subcategory.setSubCategoryType(config.getType());
                subcategory.setSequence(config.getSequence());
                subcategory.setRestaurantDetails(tempUser.getRestDetails());
                return subcategoryRepository.save(subcategory);
            case Cuisine:
                Cuisine cuisine = new Cuisine();
                cuisine.setCuisineName(config.getName());
                cuisine.setCuisineType(config.getType());
                cuisine.setSequence(config.getSequence());
                cuisine.setRestaurantDetails(tempUser.getRestDetails());
                return cuisineRepository.save(cuisine);
            case QuantityOption:
                QuantityOption quantityoption = new QuantityOption();
                quantityoption.setQuantityOptionName(config.getName());
                quantityoption.setQuantityOptionType(config.getType());
                quantityoption.setSequence(config.getSequence());
                quantityoption.setRestaurantDetails(tempUser.getRestDetails());
                return quantityoptionRepository.save(quantityoption);
            default:
                throw new IllegalArgumentException("invalid argument");
        }

    }
    public Object addTable(RestTableDTO tableConfig) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
        RestTable restTable = new RestTable();
        restTable.setTableNumber(tableConfig.getTableNumber());
        restTable.setTableSize(tableConfig.getTableSize());
        restTable.setQrCode(tableConfig.getQrCode());
        restTable.setRestaurantDetails(tempUser.getRestDetails());
        return restTableRepository.save(restTable);
    }



    public HashMap<String,Object> getConfig() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restUser = userRepository.findByUsername(auth.getName());
        HashMap<String,Object> configList=new HashMap<>();
        List<HashMap<String,Object>>catList=new ArrayList<>();
        List<HashMap<String,Object>>subCatList=new ArrayList<>();
        List<HashMap<String,Object>>cuisineList=new ArrayList<>();
        List<HashMap<String,Object>>quanityOpList=new ArrayList<>();
        for(Category catItem:restUser.getRestDetails().getCategories()) {
            HashMap<String,Object> cat=new HashMap<>();
            cat.put("id",catItem.getId());
            cat.put("categoryName",catItem.getCategoryName());
            cat.put("categoryType",catItem.getCategoryType());
            cat.put("sequence",catItem.getSequence());
            catList.add(cat);
        }
        for(SubCategory subCatItem:restUser.getRestDetails().getSubCategories()) {
            HashMap<String,Object> subcat=new HashMap<>();
            subcat.put("id",subCatItem.getId());
            subcat.put("subCategoryName",subCatItem.getSubCategoryName());
            subcat.put("subCategoryType",subCatItem.getSubCategoryType());
            subcat.put("sequence",subCatItem.getSequence());
            subCatList.add(subcat);
        }
        for(Cuisine cuisineItem:restUser.getRestDetails().getCuisines()) {
            HashMap<String,Object> cusine=new HashMap<>();
            cusine.put("id",cuisineItem.getId());
            cusine.put("cuisineName",cuisineItem.getCuisineName());
            cusine.put("cuisineType",cuisineItem.getCuisineType());
            cusine.put("sequence",cuisineItem.getSequence());
            cuisineList.add(cusine);
        }
        for(QuantityOption qOItem:restUser.getRestDetails().getQuantityOptions()) {
            HashMap<String,Object> quantityop=new HashMap<>();
            quantityop.put("id",qOItem.getId());
            quantityop.put("quantityOptionName",qOItem.getQuantityOptionName());
            quantityop.put("quantityOptionType",qOItem.getQuantityOptionType());
            quantityop.put("sequence",qOItem.getSequence());
            quanityOpList.add(quantityop);
        }
        configList.put("categories",catList);
        configList.put("subCategories",subCatList);
        configList.put("cuisines",cuisineList);
        configList.put("quantityOptions",quanityOpList);
        return configList;
    }

    public List<RestTable> getAllTables() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
        return tempUser.getRestDetails().getRestTables();
    }
}
