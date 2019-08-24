package com.wityorestaurant.modules.restaurant.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityorestaurant.common.Constant;
import com.wityorestaurant.modules.config.service.RestaurantConfigurationService;
import com.wityorestaurant.modules.restaurant.dto.LoginRequestDTO;
import com.wityorestaurant.modules.restaurant.dto.RegistrationDTO;
import com.wityorestaurant.modules.restaurant.dto.RestaurantListDto;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import com.wityorestaurant.modules.restaurant.service.RestaurantUserService;
import com.wityorestaurant.security.config.JwtTokenProvider;
import com.wityorestaurant.security.dto.JwtSuccessDto;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/restaurant")
public class RestaurantController {
    @Autowired
    RestaurantUserService restUserServiceImpl;

    @Autowired
    RestaurantConfigurationService restConfigServiceImpl;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    RestaurantUserRepository userRepo;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO user, BindingResult result) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = Constant.TOKEN_PREFIX + tokenProvider.generateJwtToken(auth);
        return ResponseEntity.ok(new JwtSuccessDto(true, jwt));
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO customer){
        restUserServiceImpl.saveUser(customer);
        Map<String,String> response = new HashMap<>();
        response.put("Message", "New restaurant got on-boarded successfully");
        return ResponseEntity.accepted().body(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deRegister/{username}")
    public ResponseEntity<?> deRegisterUser(@PathVariable(value = "username")String username){
        restUserServiceImpl.removeUser(username);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/restaurants")
    public ResponseEntity<?> listUser(HttpServletRequest request){
        //request.isUserInRole("ADMIN");
        return new ResponseEntity<Object>(restUserServiceImpl.fetchAllUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/myrestaurant")
    public Object getMyRestaurant() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser user = userRepo.findByUsername(auth.getName());
        return user.getRestDetails();

    }
    
    @GetMapping("/restaurant-id-list")
    public ResponseEntity<?> restDetailsByIdName(){
    	return new ResponseEntity<RestaurantListDto>(restUserServiceImpl.getAllRestaurantIdsAndName(), HttpStatus.OK);
    }
}
