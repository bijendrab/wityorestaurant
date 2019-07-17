package com.wityorestaurant.modules.restaurant.service;

import com.wityorestaurant.modules.restaurant.dto.ConfigurationDTO;

import java.util.HashMap;
import java.util.List;

public interface RestaurantConfigurationService {
    HashMap<String,Object> getConfig();

    Object add(ConfigurationDTO config);
}
