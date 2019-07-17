package com.wityorestaurant.modules.restaurant.controller;

import com.google.gson.Gson;
import com.wityorestaurant.common.Constant;
import com.wityorestaurant.modules.restaurant.dto.ConfigurationDTO;
import com.wityorestaurant.modules.restaurant.dto.LoginRequestDTO;
import com.wityorestaurant.modules.restaurant.dto.RegistrationDTO;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import com.wityorestaurant.modules.restaurant.service.RestaurantConfigurationService;
import com.wityorestaurant.modules.restaurant.service.RestaurantUserService;
import com.wityorestaurant.security.config.JwtTokenProvider;
import com.wityorestaurant.security.dto.JwtSuccessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO customer){
        restUserServiceImpl.saveUser(customer);
        Map<String,String> response = new HashMap<>();
        response.put("Message", "New restaurant got on-boarded successfully");
        return ResponseEntity.accepted().body(response);
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
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/addconfig")
    public Object addConfig(@RequestBody ConfigurationDTO config) {
        return restConfigServiceImpl.add(config);

    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getconfig")
    public ResponseEntity<?> getConfig() {
        return new ResponseEntity<Object>(restConfigServiceImpl.getConfig(),HttpStatus.FOUND);

    }
}
