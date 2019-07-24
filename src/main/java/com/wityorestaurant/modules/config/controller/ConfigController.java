package com.wityorestaurant.modules.config.controller;
import com.wityorestaurant.modules.config.dto.ConfigurationDTO;
import com.wityorestaurant.modules.config.service.RestaurantConfigurationService;
import com.wityorestaurant.modules.user.repository.RestaurantUserRepository;
import com.wityorestaurant.modules.user.service.RestaurantUserService;
import com.wityorestaurant.security.config.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/restaurant")
public class ConfigController {
    @Autowired
    RestaurantUserService restUserServiceImpl;

    @Autowired
    RestaurantConfigurationService restConfigServiceImpl;

    @Autowired
    AuthenticationManager authManager;


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
}
