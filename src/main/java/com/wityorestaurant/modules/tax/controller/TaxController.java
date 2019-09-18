package com.wityorestaurant.modules.tax.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityorestaurant.modules.tax.model.TaxProfile;
import com.wityorestaurant.modules.tax.service.TaxService;

@RestController
@RequestMapping("/api/tax")
@CrossOrigin("*")
public class TaxController {
	
	@Autowired
	private TaxService taxService;
	
	@PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-profiles")
	public ResponseEntity<?> getTaxProfiles(@RequestBody TaxProfile profile){
    	return new ResponseEntity<List<TaxProfile>>(taxService.getTaxProfiles(),HttpStatus.OK);
    }
	
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add-profile")
	public ResponseEntity<TaxProfile> addTaxProfile(@RequestBody TaxProfile profile){
    	return new ResponseEntity<TaxProfile>(taxService.addTaxProfile(profile),HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/edit-profile")
	public ResponseEntity<TaxProfile> editTaxProfile(@RequestBody TaxProfile profile){
    	return new ResponseEntity<TaxProfile>(taxService.editTaxProfile(profile),HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/delete-profile/{profileId}")
	public ResponseEntity<Boolean> deleteTaxProfile(@PathVariable Long profileId){
    	return new ResponseEntity<Boolean>(taxService.deleteTaxProfile(profileId),HttpStatus.OK);
    }

}
