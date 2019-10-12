package com.wityorestaurant.modules.tax.service.impl;

import java.util.List;
import java.util.Set;
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
	private RestaurantUserRepository userRepository;
	
	
	private Logger logger = LoggerFactory.getLogger(TaxServiceImpl.class);
	
	public List<TaxProfile> getTaxProfiles(){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        RestaurantUser restUser = userRepository.findByUsername(auth.getName());
			return taxRepository.findProfilesByRestId(restUser.getRestDetails().getRestId());
		} catch (Exception e) {
			logger.error("Exception in getTaxProfiles => {}", e);
		}
		return null;
	}
	
	public TaxProfile addTaxProfile(TaxProfile profile) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        RestaurantUser restUser = userRepository.findByUsername(auth.getName());
	        profile.setRestaurant(restUser.getRestDetails());
	        profile.getTaxComponents().forEach(taxComp -> {
	        	taxComp.setTaxProfile(profile);
	        });
	        return taxRepository.save(profile);
		} catch (Exception e) {
			logger.error("Exception in addTaxProfile => {}", e);
		}
		return null;
	}
	
	public TaxProfile editTaxProfile(TaxProfile taxProfile) {
		try {
			TaxProfile tProfile = taxRepository.findById(taxProfile.getTaxProfileId()).get();
			taxProfile.getTaxComponents().forEach(component -> {
				component.setTaxProfile(tProfile);
			});
			
			for(TaxComponent temp : tProfile.getTaxComponents()) {
				boolean isPresent = false;
				for(TaxComponent temp2 : taxProfile.getTaxComponents()) {
					if(temp.getTaxComponentId() == temp2.getTaxComponentId()) {
						isPresent = true;
						if(!temp2.getComponentName().equals(temp.getComponentName())){
							temp.setComponentName(temp2.getComponentName());
						}
						if(temp2.getWeightage() != temp.getWeightage()) {
							temp.setWeightage(temp2.getWeightage());
						}
					}
				}
				if(isPresent == false) {
					tProfile.getTaxComponents().add(temp);
				}
			}
			tProfile.setAppliedOn(taxProfile.getAppliedOn());
			return taxRepository.save(tProfile);
		} catch (Exception e) {
			logger.error("Exception in editTaxProfile => {}", e);
		}
		return null;
	}
	
	/*public TaxProfile removeComponentsFromTaxProfile(Long taxProfileId, Long taxComponentId) {
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
	}*/

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
