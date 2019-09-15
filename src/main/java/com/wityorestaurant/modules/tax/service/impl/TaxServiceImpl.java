package com.wityorestaurant.modules.tax.service.impl;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import com.wityorestaurant.modules.tax.model.TaxComponent;
import com.wityorestaurant.modules.tax.model.TaxProfile;
import com.wityorestaurant.modules.tax.repository.TaxRepository;
import com.wityorestaurant.modules.tax.service.TaxService;

@Service
public class TaxServiceImpl implements TaxService {
	
	@Autowired
	private TaxRepository taxRepository;
	
	@Autowired
	private RestaurantUserRepository userRepo;
	
	private Logger logger = LoggerFactory.getLogger(TaxServiceImpl.class);
	
	public TaxProfile addTaxProfile(TaxProfile profile) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        RestaurantUser restUser = userRepo.findByUsername(auth.getName());
	        profile.setRestaurant(restUser.getRestDetails());
	        return taxRepository.save(profile);
		} catch (Exception e) {
			logger.error("Exception in addTaxProfile => {}", e);
		}
		return null;
	}
	
	public TaxProfile editTaxProfile(TaxProfile taxProfile) {
		try {
			return taxRepository.save(taxProfile);
		} catch (Exception e) {
			logger.error("Exception in editTaxProfile => {}", e);
		}
		return null;
	}
	
	public TaxProfile removeComponentsFromTaxProfile(Long taxProfileId, Long taxComponentId) {
		try {
			TaxProfile profile = taxRepository.findById(taxProfileId).get();
			TaxComponent component = null;
			for(TaxComponent comp: profile.getTaxComponents()) {
				if(comp.getTaxComponentId() == taxComponentId){
					component = comp;
					break;
				}
			}
			if(component!=null) {
				Long compId = component.getTaxComponentId();
				profile.setTaxComponents(profile.getTaxComponents()
				.parallelStream()
				.filter(comp ->
				comp.getTaxComponentId() != compId)
				.collect(Collectors.toList()));
			}
			return taxRepository.save(profile);
		} catch (Exception e) {
			logger.error("Exception in removeTaxComponent => {}", e);
		}
		return null;
	}

	public Boolean deleteTaxProfile(Long taxProfileId) {
		try {
			taxRepository.deleteTaxProfile(taxProfileId);
			return Boolean.TRUE;
		} catch (Exception e) {
			logger.error("Exception in removeTaxProfile => {}", e);
		}
		return Boolean.FALSE;
	}
}