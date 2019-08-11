package com.wityorestaurant.modules.config.controller;
import com.wityorestaurant.modules.config.dto.RestTableDTO;
import com.wityorestaurant.modules.config.model.Staff;

import java.util.List;

import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityorestaurant.modules.config.dto.ConfigurationDTO;
import com.wityorestaurant.modules.config.service.RestaurantConfigurationService;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import com.wityorestaurant.modules.restaurant.service.RestaurantUserService;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/restaurant-config")
public class ConfigController {
    @Autowired
    RestaurantUserService restUserServiceImpl;

    @Autowired
    RestaurantConfigurationService restConfigServiceImpl;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    RestTableRepository restTableRepository;

    @Autowired
    RestaurantUserRepository userRepo;


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/addconfig")
    public ResponseEntity<?> addConfig(@RequestBody ConfigurationDTO config) {
        return new ResponseEntity<>(restConfigServiceImpl.add(config),HttpStatus.ACCEPTED);

    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getconfig")
    public ResponseEntity<?> getConfig() {
        return new ResponseEntity<Object>(restConfigServiceImpl.getConfig(),HttpStatus.ACCEPTED);

    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/addTable")
    public ResponseEntity<?> addTable(@RequestBody RestTableDTO restTableConfig) {
        return new ResponseEntity<>(restConfigServiceImpl.addTable(restTableConfig),HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getTables")
    public ResponseEntity<?> getTables() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restUser = userRepo.findByUsername(auth.getName());
        return new ResponseEntity<>(restTableRepository.findByRestaurantId(restUser.getRestDetails().getRestId()),HttpStatus.ACCEPTED);
    }


    @GetMapping("/{restaurantId}/getTables")
    public ResponseEntity<?> getTables(@PathVariable("restaurantId") Long restId) {
        return new ResponseEntity<>(restTableRepository.findByRestaurantId(restId),HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/staff/add/{restaurantId}")
    public ResponseEntity<?> addStaff(@RequestBody Staff staff, @PathVariable Long restaurantId) {
        return new ResponseEntity<Staff>(restConfigServiceImpl.addNewStaff(staff, restaurantId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/staff/delete/{staffId}")
    public ResponseEntity<?> removeStaff(@PathVariable Long staffId) {
        return new ResponseEntity<Boolean>(restConfigServiceImpl.deleteStaffById(staffId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/staff/update")
    public ResponseEntity<?> updateStaff(@RequestBody Staff staff) {
        return new ResponseEntity<Staff>(restConfigServiceImpl.updateStaff(staff), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/staff/get-all-staffs")
    public ResponseEntity<?> getAllStaff() {
        return new ResponseEntity<List<Staff>>(restConfigServiceImpl.getAllStaffs(), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/staff/get-custom-staffs")
    public ResponseEntity<?> getCustomStaffs() {
        return new ResponseEntity<List<Staff>>(restConfigServiceImpl.getCustomStaffs(), HttpStatus.OK);
    }
}
