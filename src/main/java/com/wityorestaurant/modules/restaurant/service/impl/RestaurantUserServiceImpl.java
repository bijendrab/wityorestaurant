package com.wityorestaurant.modules.restaurant.service.impl;

import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.restaurant.dto.RegistrationDTO;
import com.wityorestaurant.modules.restaurant.dto.RestaurantBasicDTO;
import com.wityorestaurant.modules.restaurant.dto.RestaurantListDto;
import com.wityorestaurant.modules.restaurant.exception.UsernameAlreadyExistsException;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.model.Role;
import com.wityorestaurant.modules.restaurant.model.RoleName;
import com.wityorestaurant.modules.restaurant.repository.RestaurantRepository;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import com.wityorestaurant.modules.restaurant.repository.RoleRepository;
import com.wityorestaurant.modules.restaurant.service.RestaurantUserService;
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
    private RestaurantRepository restaurantRepository;

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

            if (tempUser == null) {
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
                restDetails.setGstIn(newUser.getGstIn());
                user.setEnabled(TRUE);
                user.setPassword(encoder.encode(newUser.getPassword()));
                Role userRole = roleRepository.findByName(RoleName.ROLE_USER);
                user.setRoles(Collections.singleton(userRole));
                user.setRestDetails(restDetails);
                restDetails.setUser(user);
                restDetails.setCart(new RestaurantCart());
                return userRepository.save(user);
            } else {
                throw new UsernameAlreadyExistsException("This username already exists, try with some other username");
            }
        } catch (Exception e) {
            if (e.getClass().equals(UsernameAlreadyExistsException.class)) {
                throw new UsernameAlreadyExistsException(e.getMessage());
            } else {
                throw new RuntimeException("Server not responding, please try again later");
            }
        }
    }

    public RestaurantListDto getAllRestaurantIdsAndName() {
        try {
            RestaurantListDto dto = new RestaurantListDto();
            List<RestaurantBasicDTO> responseList = new ArrayList<>();
            List<RestaurantDetails> restaurantList = restaurantRepository.findAll();
            if (restaurantList.size() != 0) {
                for (RestaurantDetails restaurantUser : restaurantList) {
                    if (restaurantUser.getRestName().equals("adminrest")){
                        continue;
                    }
                    RestaurantBasicDTO restaurantBasicDTO = new RestaurantBasicDTO();
                    restaurantBasicDTO.setRestName(restaurantUser.getRestName());
                    restaurantBasicDTO.setPhone(restaurantUser.getPhone());
                    restaurantBasicDTO.setDescription(restaurantUser.getDescription());
                    restaurantBasicDTO.setAddress1(restaurantUser.getAddress1());
                    restaurantBasicDTO.setAddress2(restaurantUser.getAddress2());
                    restaurantBasicDTO.setCity(restaurantUser.getCity());
                    restaurantBasicDTO.setGstIn(restaurantUser.getGstIn());
                    responseList.add(restaurantBasicDTO);
                }
                dto.setRestaurantDetails(responseList);
                return dto;
            }
        } catch (Exception e) {
        }
        return null;
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
