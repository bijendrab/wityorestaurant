package com.wityorestaurant.modules.discount.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.wityorestaurant.modules.discount.model.DiscountItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wityorestaurant.modules.config.service.impl.RestaurantConfigurationServiceImpl;
import com.wityorestaurant.modules.discount.model.Discount;
import com.wityorestaurant.modules.discount.repository.DiscountRepository;
import com.wityorestaurant.modules.discount.service.DiscountService;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;

@Service
public class DiscountServiceImpl implements DiscountService {

	Logger logger = LoggerFactory.getLogger(RestaurantConfigurationServiceImpl.class);
	
	@Autowired
	private DiscountRepository discountRepository;

	@Autowired
	private RestaurantUserRepository userRepo;

	@Autowired
	private RestaurantUserRepository userRepository;

	public List<Discount> getAllDiscounts() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			RestaurantUser restUser = userRepository.findByUsername(auth.getName());
			return discountRepository.findDiscountsByRestId(restUser.getRestDetails().getRestId());
		}
		catch (Exception e) {
			logger.error("Exception in getDiscounts => {}", e);
		}
		return null;
	}

	public Discount insertDiscountRecord(Discount discount) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		RestaurantUser restUser = userRepo.findByUsername(auth.getName());
		RestaurantDetails restaurant = restUser.getRestDetails();
		discount.setRestaurant(restaurant);
		discount.getDiscountedItems().forEach(item -> {
			item.setDiscount(discount);
		});
		return discountRepository.save(discount);
	}
	
	public Discount updateDiscount(Discount updatedDiscount) {
		try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            RestaurantUser tempUser = userRepo.findByUsername(auth.getName());
            Long restaurantId = tempUser.getRestDetails().getRestId();
            Discount updateObj = discountRepository.findRestaurantDiscountById(updatedDiscount.getDiscountId(), restaurantId);
            updateObj.setDiscountDescription(updatedDiscount.getDiscountDescription());
            updateObj.setEndOption(updatedDiscount.getEndOption());
            updateObj.setEndDate(updatedDiscount.getEndDate());
            updateObj.setEndTime(updatedDiscount.getEndTime());
            updateObj.setStartDate(updatedDiscount.getStartDate());
            updateObj.setStartTime(updatedDiscount.getStartTime());
			updateObj.setIsEnabled(updatedDiscount.getIsEnabled());
			updateObj.setDaysOfMonth(updatedDiscount.getDaysOfWeek());
			updateObj.setDiscountType(updatedDiscount.getDiscountValueType());
			updateObj.setDaysOfWeek(updatedDiscount.getDaysOfWeek());
			updateObj.setFrequency(updatedDiscount.getFrequency());
			updateObj.setDiscountName(updatedDiscount.getDiscountName());
			updateObj.setDiscountValue(updatedDiscount.getDiscountValue());
			updateObj.setDiscountValueType(updatedDiscount.getDiscountValueType());
			Set<DiscountItem> discountItems = new HashSet<DiscountItem>();
            updatedDiscount.getDiscountedItems().forEach(item -> {
    			item.setDiscount(updatedDiscount);
				discountItems.add(item);
    		});
            return discountRepository.save(updateObj);
            
        } catch (Exception e) {
            logger.error("UnableToUpdateDiscountException: {}", e.getMessage());
        }
        return null;
	}
	 
	public boolean enableDisableDiscount(int discountId) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            RestaurantUser tempUser = userRepo.findByUsername(auth.getName());
            Long restaurantId = tempUser.getRestDetails().getRestId();
            Discount updateObj = discountRepository.findRestaurantDiscountById(discountId, restaurantId);
            updateObj.setIsEnabled(!updateObj.getIsEnabled());
            discountRepository.save(updateObj);
            return true;
		} catch (Exception e) {
			 logger.error("UnableToUpdateDiscountException: {}", e.getMessage());
			 return false;
		}
	}
	

}
