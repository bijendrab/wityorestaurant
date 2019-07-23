package com.wityorestaurant.modules.user.service.impl;

import com.wityorestaurant.modules.user.dto.RegistrationDTO;
import com.wityorestaurant.modules.user.exception.UsernameAlreadyExistsException;
import com.wityorestaurant.modules.user.model.RestaurantDetails;
import com.wityorestaurant.modules.user.model.RestaurantUser;
import com.wityorestaurant.modules.user.model.Role;
import com.wityorestaurant.modules.user.model.RoleName;
import com.wityorestaurant.modules.user.repository.RestaurantUserRepository;
import com.wityorestaurant.modules.user.repository.RoleRepository;
import com.wityorestaurant.modules.user.service.RestaurantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Boolean.TRUE;
@Service(value = "RestaurantUserService")
public class RestaurantUserServiceImpl implements RestaurantUserService {
    @Autowired
    private RestaurantUserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    RestaurantUserRepository userRepo;

    @Override
    public List<RestaurantUser> fetchAllUsers() {
        List<RestaurantUser> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public RestaurantUser saveUser(RegistrationDTO newUser) {
        try {
            RestaurantUser user = new RestaurantUser();
            RestaurantDetails restDetails = new RestaurantDetails();
            user.setEmailId(newUser.getUsername());
            //user.setPassword(newUser.getPassword());

            RestaurantUser tempUser = userRepository.findByUsername(user.getUsername());

            if(tempUser == null) {
                restDetails.setRestName(newUser.getRestName());
                restDetails.setOwnerName(newUser.getOwnerName());
                restDetails.setPhone(newUser.getPhone());
                restDetails.setEmail(newUser.getEmail());
                restDetails.setAddress1(newUser.getAddress1());
                restDetails.setAddress2(newUser.getAddress2());
                restDetails.setCity(newUser.getCity());
                restDetails.setState(newUser.getState());
                restDetails.setPin(newUser.getPin());
                restDetails.setBankAccountNumber(newUser.getBankAccountNumber());
                restDetails.setBankName(newUser.getBankName());
                restDetails.setBankIfscCode(newUser.getBankIfscCode());
                user.setEnabled(TRUE);
                user.setPassword(encoder.encode(newUser.getPassword()));
                Role userRole = roleRepository.findByName(RoleName.ROLE_USER);
                user.setRoles(Collections.singleton(userRole));
                user.setRestDetails(restDetails);
                restDetails.setUser(user);
                return userRepository.save(user);
            } else {
                throw new UsernameAlreadyExistsException("This username already exists, try with some other username");
            }
        }catch (Exception e) {
            if(e.getClass().equals(UsernameAlreadyExistsException.class)) {
                throw new UsernameAlreadyExistsException(e.getMessage());
            } else {
                throw new RuntimeException("Server not responding, please try again later");
            }
        }
    }

    //@Override
    public void removeUser(String username) {
        userRepository.delete(userRepo.findByUsername(username));
    }

    @Override
    public RestaurantUser getUserByUsername(String username) {
        return null;
    }

}
