package com.wityorestaurant.modules.menu.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import com.wityorestaurant.modules.menu.model.AddOnItems;
import com.wityorestaurant.modules.menu.model.AddOnProfile;
import com.wityorestaurant.modules.menu.repository.AddOnRepository;
import com.wityorestaurant.modules.menu.service.RestaurantAddOnProfileService;
import com.wityorestaurant.modules.restaurant.model.RestaurantUser;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "RestaurantAddOnProfileService")
public class RestaurantAddOnProfileServiceImpl implements RestaurantAddOnProfileService {
    @Autowired
    private AddOnRepository addOnRepository;
    @Autowired
    private RestaurantUserRepository userRepository;

    public List<AddOnProfile> getAllAddOnProfiles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
        return tempUser.getRestDetails().getAddOnProfiles();
    }

    public AddOnProfile getAddOnProfileItemById(String ProfileId) {
        AddOnProfile addOnProfile = getMenuItem(ProfileId);
        return addOnProfile;
    }

    private AddOnProfile getMenuItem(String ProfileId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
        return addOnRepository.findAddOnProfileByProfileAndRestId(ProfileId, tempUser.getRestDetails().getRestId());
    }

    @Transactional
    public String deleteAddOnProfileItem(String ProfileId) {
        try {
            addOnRepository.delete(getMenuItem(ProfileId));
            return "Add On Profile deleted";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public AddOnProfile addAddOnProfileItem(AddOnProfile addOnProfile) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
        String profileUUID = UUID.randomUUID().toString();
        profileUUID = profileUUID.replaceAll("-", "");
        addOnProfile.setRestaurantDetails(tempUser.getRestDetails());
        addOnProfile.setProfileId(profileUUID);
        setMenu(addOnProfile);
        return addOnRepository.save(addOnProfile);
    }

    public AddOnProfile editAddOnProfileItem(AddOnProfile addOnProfile) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RestaurantUser tempUser = userRepository.findByUsername(auth.getName());
        addOnProfile.setRestaurantDetails(tempUser.getRestDetails());
        setMenu(addOnProfile);
        return addOnRepository.save(addOnProfile);
    }

    public void setMenu(AddOnProfile addOnProfile) {
        Set<AddOnItems> pqo = new HashSet<>();
        for (AddOnItems addOnItem : addOnProfile.getCustomItems()) {
            addOnItem.setAddOnProfile(addOnProfile);
            pqo.add(addOnItem);
        }
    }

    public String setAddOnProfileToggleStatus(String ProfileId) {
        AddOnProfile addOnProfile = getAddOnProfileItemById(ProfileId);
        if (addOnProfile.getToggleAddOnItems().equals(true)) {
            addOnProfile.setToggleAddOnItems(false);
            addOnRepository.save(addOnProfile);
            return "AddOn Profile Toggle Status is Disabled";
        } else {
            addOnProfile.setToggleAddOnItems(true);
            addOnRepository.save(addOnProfile);
            return "AddOn Profile Toggle Status is Enabled";
        }
    }
}
