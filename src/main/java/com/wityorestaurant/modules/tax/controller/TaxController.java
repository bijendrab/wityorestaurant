package com.wityorestaurant.modules.tax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wityorestaurant.modules.tax.model.TaxProfile;
import com.wityorestaurant.modules.tax.service.TaxService;

@RestController("/api/tax")
@CrossOrigin("*")
public class TaxController {
	
	@Autowired
	private TaxService taxService;
	
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add-profile")
	public ResponseEntity<TaxProfile> addTaxProfile(@RequestBody TaxProfile profile){
    	return new ResponseEntity<TaxProfile>(taxService.addTaxProfile(profile),HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/edit-profile")
	public ResponseEntity<TaxProfile> editTaxProfile(@RequestBody TaxProfile profile){
    	return new ResponseEntity<TaxProfile>(taxService.editTaxProfile(profile),HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/delete-profile/{profileId}")
	public ResponseEntity<Boolean> deleteTaxProfile(@PathVariable Long profileId){
    	return new ResponseEntity<Boolean>(taxService.deleteTaxProfile(profileId),HttpStatus.OK);
    }

}
