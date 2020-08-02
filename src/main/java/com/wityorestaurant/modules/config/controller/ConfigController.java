package com.wityorestaurant.modules.config.controller;

import com.wityorestaurant.modules.config.dto.ConfigurationDTO;
import com.wityorestaurant.modules.config.dto.PaymentModeDTO;
import com.wityorestaurant.modules.config.dto.RestTableDTO;
import com.wityorestaurant.modules.config.model.PaymentMode;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.config.model.Staff;
import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.config.service.RestaurantConfigurationService;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import com.wityorestaurant.modules.restaurant.service.RestaurantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        return new ResponseEntity<>(restConfigServiceImpl.add(config), HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getconfig")
    public ResponseEntity<?> getConfig() {
        return new ResponseEntity<Object>(restConfigServiceImpl.getConfig(), HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/addTable")
    public ResponseEntity<?> addTable(@RequestBody RestTableDTO restTableConfig) {
        return new ResponseEntity<>(restConfigServiceImpl.addTable(restTableConfig), HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getTables")
    public ResponseEntity<List<RestTable>> getTables() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restUser = userRepo.findByUsername(auth.getName());
        return new ResponseEntity<>(restTableRepository.findByRestaurantId(restUser.getRestDetails().getRestId()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{restaurantId}/getTables")
    public ResponseEntity<List<RestTable>> getTables(@PathVariable("restaurantId") Long restId) {
        return new ResponseEntity<>(restTableRepository.findByRestaurantId(restId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-table/{tableId}")
    public ResponseEntity<RestTable> getTable(@PathVariable Long tableId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restUser = userRepo.findByUsername(auth.getName());
        RestaurantDetails restaurant = restUser.getRestDetails();
        return new ResponseEntity<>(restTableRepository.findByRestaurantIdAndTableId(tableId, restaurant.getRestId()), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/staff/add")
    public ResponseEntity<Staff> addStaff(@RequestBody Staff staff) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restUser = userRepo.findByUsername(auth.getName());
        RestaurantDetails restaurant = restUser.getRestDetails();
        return new ResponseEntity<>(restConfigServiceImpl.addNewStaff(staff, restaurant.getRestId()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/staff/delete/{staffId}")
    public ResponseEntity<Boolean> removeStaff(@PathVariable Long staffId) {
        return new ResponseEntity<>(restConfigServiceImpl.deleteStaffById(staffId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/staff/update")
    public ResponseEntity<Staff> updateStaff(@RequestBody Staff staff) {
        return new ResponseEntity<>(restConfigServiceImpl.updateStaff(staff), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/staff/get-all-staffs")
    public ResponseEntity<List<Staff>> getAllStaff() {
        return new ResponseEntity<>(restConfigServiceImpl.getAllStaffs(), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/staff/get-custom-staffs")
    public ResponseEntity<List<Staff>> getCustomStaffs() {
        return new ResponseEntity<>(restConfigServiceImpl.getCustomStaffs(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/table/update-table-charges")
    public ResponseEntity<RestTable> updateTableCharges(@RequestBody RestTable table) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restUser = userRepo.findByUsername(auth.getName());
        RestaurantDetails restaurant = restUser.getRestDetails();
        return new ResponseEntity<>(restConfigServiceImpl.updateTableCharges(table, restaurant.getRestId()), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/table/edit-table")
    public ResponseEntity<?> updateTable(@RequestBody RestTable table){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restUser = userRepo.findByUsername(auth.getName());
        RestaurantDetails restaurant = restUser.getRestDetails();
    	return new ResponseEntity<RestTable>(restConfigServiceImpl.updateTableById(table.getId(), restaurant.getRestId(), table), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/table/remove-table/{tableId}")
    public ResponseEntity<?> deleteTable(@PathVariable Long tableId){
    	return new ResponseEntity<Boolean>(restConfigServiceImpl.deleteTableById(tableId), HttpStatus.OK);
    }



    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getPaymentMethod")
    public ResponseEntity<List<PaymentMode>> getPaymentMethod() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restUser = userRepo.findByUsername(auth.getName());
        Long restId = restUser.getRestDetails().getRestId();
        return new ResponseEntity<>(restConfigServiceImpl.getPaymentMethod(restId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/addPaymentMethod")
    public ResponseEntity<PaymentMode> addPaymentMethod(@RequestBody PaymentModeDTO paymentModeDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restUser = userRepo.findByUsername(auth.getName());
        RestaurantDetails restaurantDetails = restUser.getRestDetails();
        return new ResponseEntity<>(restConfigServiceImpl.addPaymentMethod(paymentModeDTO,restaurantDetails), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/deletePaymentMethod/{paymentId}")
    public ResponseEntity<Boolean> deletePaymentMethod(@PathVariable Long paymentId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restUser = userRepo.findByUsername(auth.getName());
        Long restId = restUser.getRestDetails().getRestId();
        return new ResponseEntity<>(restConfigServiceImpl.deletePaymentMethod(paymentId,restId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/setPaymentMethodStatus/{paymentId}")
    public ResponseEntity<Boolean> setPaymentMethodStatus(@PathVariable(value = "paymentId") Long paymentId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser restUser = userRepo.findByUsername(auth.getName());
        Long restId = restUser.getRestDetails().getRestId();
        return new ResponseEntity<>(restConfigServiceImpl.setPaymentMethodStatus(paymentId,restId), HttpStatus.OK);
    }
}
