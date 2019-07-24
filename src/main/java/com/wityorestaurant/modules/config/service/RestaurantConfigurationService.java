package com.wityorestaurant.modules.config.service;

import com.wityorestaurant.modules.config.dto.ConfigurationDTO;

import java.util.HashMap;

public interface RestaurantConfigurationService {
    HashMap<String,Object> getConfig();

    Object add(ConfigurationDTO config);
}
