package com.wityorestaurant.modules.menu.controller;

import com.wityorestaurant.modules.menu.model.AddOnProfile;
import com.wityorestaurant.modules.menu.service.RestaurantAddOnProfileService;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import com.wityorestaurant.modules.restaurant.service.RestaurantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/addOnProfile")
public class AddOnProfileController {
    @Autowired
    RestaurantUserService restUserServiceImpl;

    @Autowired
    RestaurantAddOnProfileService restaurantAddOnProfileService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    RestaurantUserRepository userRepo;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/addAddOnProfile")
    public ResponseEntity<?> addAddOnProfile(@RequestBody AddOnProfile addOnProfile) {
        return new ResponseEntity<>(restaurantAddOnProfileService.addAddOnProfileItem(addOnProfile), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getAddOnProfiles")
    public ResponseEntity<?> getAddOnProfiles() {
        return new ResponseEntity<>(restaurantAddOnProfileService.getAllAddOnProfiles(), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getAddOnProfile/{profileId}")
    public ResponseEntity<?> getAddOnProfile(@PathVariable(value = "profileId") String profileId) {
        return new ResponseEntity<>(restaurantAddOnProfileService.getAddOnProfileItemById(profileId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/editAddOnProfile")
    public ResponseEntity<?> editAddOnProfile(@RequestBody AddOnProfile addOnProfile) {
        return new ResponseEntity<>(restaurantAddOnProfileService.editAddOnProfileItem(addOnProfile), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/deleteAddOnProfile/{profileId}")
    public ResponseEntity<?> deleteAddOnProfile(@PathVariable(value = "profileId") String profileId) {
        return new ResponseEntity<>(restaurantAddOnProfileService.deleteAddOnProfileItem(profileId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/setAddOnProfileToggleStatus/{addOnProfileId}")
    public ResponseEntity<?> setAddOnProfileToggleStatus(@PathVariable(value = "addOnProfileId") String addOnProfileId) {
        return new ResponseEntity<>(restaurantAddOnProfileService.setAddOnProfileToggleStatus(addOnProfileId), HttpStatus.ACCEPTED);
    }

}
