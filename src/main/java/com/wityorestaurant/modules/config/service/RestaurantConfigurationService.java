package com.wityorestaurant.modules.config.service;

import com.wityorestaurant.modules.config.dto.ConfigurationDTO;
import com.wityorestaurant.modules.config.dto.RestTableDTO;
import com.wityorestaurant.modules.config.model.RestTable;

import java.util.HashMap;
import java.util.List;

public interface RestaurantConfigurationService {
    HashMap<String,Object> getConfig();

    Object add(ConfigurationDTO config);

    Object addTable(RestTableDTO tableConfig);

    List<RestTable> getAllTables();


}
