package com.wityorestaurant.modules.config.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wityorestaurant.modules.config.dto.ConfigurationDTO;
import com.wityorestaurant.modules.config.dto.RestTableDTO;
import com.wityorestaurant.modules.config.model.Category;
import com.wityorestaurant.modules.config.model.ConfigType;
import com.wityorestaurant.modules.config.model.Cuisine;
import com.wityorestaurant.modules.config.model.QuantityOption;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.config.model.Staff;
import com.wityorestaurant.modules.config.model.SubCategory;
import com.wityorestaurant.modules.config.repository.CategoryRepository;
import com.wityorestaurant.modules.config.repository.CuisineRepository;
import com.wityorestaurant.modules.config.repository.QuantityOptionRepository;
import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.config.repository.StaffRepository;
import com.wityorestaurant.modules.config.repository.SubCategoryRepository;
import com.wityorestaurant.modules.config.service.RestaurantConfigurationService;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantRepository;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;

@Service(value = "RestaurantConfigurationService")
public class RestaurantConfigurationServiceImpl implements RestaurantConfigurationService {
	
	
	Logger logger = LoggerFactory.getLogger(RestaurantConfigurationServiceImpl.class);
	
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
    @Autowired
    private StaffRepository staffRepository;
    @Autowired 
    private RestaurantRepository restaurantRepository;

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
    
    /*=========================STAFF RELATED CODING: BEGINS=============================*/
    
    public Staff addNewStaff(Staff staff, Long restaurantId) {
    	try {
    		Optional<RestaurantDetails> oRestaurant = restaurantRepository.findById(restaurantId);
    		staff.setRetaurant(oRestaurant.get());
			return staffRepository.save(staff);
		} catch (Exception e) {
			logger.error("UnableToAddStaffException: {}", e.getMessage());
		}
    	return null;
    }
    
    public Staff updateStaff(Staff updatedStaff) {
    	try {
    		Optional<Staff> oStaff = staffRepository.findById(updatedStaff.getStaffId());
    		if(oStaff.isPresent()){
    			return staffRepository.save(updatedStaff);
    		}
			
		} catch (Exception e) {
			logger.error("UnableToUpdateStaffException: {}", e.getMessage());
		}
    	return null;
    }
    
    public boolean deleteStaffById(Long staffId) {
    	try {
    		Optional<Staff> oStaff = staffRepository.findById(staffId);
    		if(oStaff.isPresent()){
    			staffRepository.delete(oStaff.get());
    			return true;
    		}
		} catch (Exception e) {
			logger.error("UnableToUpdateStaffException: {}", e.getMessage());
		}
    	return false;
    }
    
    
    /*
     * @Description: Function to return staff by roles: waiter and order taker
     * */
    public List<Staff> getCustomStaffs(){
    	try {
    		List<Staff> existingStaff = staffRepository.findAll();
        	return existingStaff.stream().filter(staff-> staff.getStaffRole().equalsIgnoreCase("Waiter")||staff.getStaffRole().equalsIgnoreCase("Order Taker")).collect(Collectors.toList());	
		} catch (Exception e) {
			logger.error("UnableToFetchStaffException: {}", e.getMessage());
		}
    	return Collections.emptyList();
    	
    }
    
    public List<Staff> getAllStaffs(){
    	try {
    		return staffRepository.findAll();
		} catch (Exception e) {
			logger.error("UnableToFetchStaffException: {}", e.getMessage());
		}
    	return Collections.emptyList();
    }
    
    public RestTable updateTableCharges(RestTable dtoTable, Long restId) {
    	RestTable table = restTableRepository.findByRestaurantIdAndTableId(dtoTable.getId(), restId);
    	if(table.isServiceChargeEnabled() == true && dtoTable.isServiceChargeEnabled() == false){
    		table.setServiceChargeEnabled(false);
    		table.setServiceCharge(0.0F);
    	} else if(table.isServiceChargeEnabled() == false && dtoTable.isServiceChargeEnabled() == true) {
    		table.setServiceChargeEnabled(true);
    		table.setServiceCharge(dtoTable.getServiceCharge());
    	}
    	
    	if(table.isPackagingChargeEnabled() == true && dtoTable.isPackagingChargeEnabled() == false){
    		table.setPackagingChargeEnabled(false);
    		table.setPackagingCharge(0.0F);
    	} else if(table.isPackagingChargeEnabled() == false && dtoTable.isPackagingChargeEnabled() == true) {
    		table.setPackagingChargeEnabled(true);
    		table.setPackagingCharge(dtoTable.getPackagingCharge());
    	}
    	
    	if(table.isOverAllDiscountEnabled() == true && dtoTable.isOverAllDiscountEnabled() == false) {
    		table.setOverAllDiscountEnabled(false);
    		table.setOverallDiscount(0.0F);
    	} else if(table.isOverAllDiscountEnabled() == false && dtoTable.isOverAllDiscountEnabled() == true) {
    		table.setOverAllDiscountEnabled(true);
    		table.setOverallDiscount(dtoTable.getOverallDiscount());
    	}
    	return restTableRepository.save(table);
    }
    
    
    
    /*=========================STAFF RELATED CODING: ENDS=============================*/
}
