package com.wityorestaurant.modules.restaurant.service;

import com.wityorestaurant.modules.restaurant.dto.ConfigurationDTO;

import java.util.HashMap;
import java.util.List;

public interface RestaurantConfigurationService {
    //public List<HashMap<String,Object>> getConfig();

    public Object add(ConfigurationDTO config);
}
