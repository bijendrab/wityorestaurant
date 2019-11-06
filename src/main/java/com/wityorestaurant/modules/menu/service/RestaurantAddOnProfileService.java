package com.wityorestaurant.modules.menu.service;

import java.util.List;
import com.wityorestaurant.modules.menu.model.AddOnProfile;

public interface RestaurantAddOnProfileService {
    List<AddOnProfile> getAllAddOnProfiles();

    AddOnProfile getAddOnProfileItemById(String ProfileId);

    String deleteAddOnProfileItem(String ProfileId);

    AddOnProfile addAddOnProfileItem(AddOnProfile addOnProfile);

    AddOnProfile editAddOnProfileItem(AddOnProfile addOnProfile);

}
