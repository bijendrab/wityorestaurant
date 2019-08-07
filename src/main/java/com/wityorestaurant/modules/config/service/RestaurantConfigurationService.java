package com.wityorestaurant.modules.config.service;

import com.wityorestaurant.modules.config.dto.ConfigurationDTO;
import com.wityorestaurant.modules.config.dto.RestTableDTO;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.config.model.Staff;

import java.util.HashMap;
import java.util.List;

public interface RestaurantConfigurationService {
    HashMap<String,Object> getConfig();

    Object add(ConfigurationDTO config);

    Object addTable(RestTableDTO tableConfig);

    List<RestTable> getAllTables();
    
    public List<Staff> getAllStaffs();
    
    public List<Staff> getCustomStaffs();
    
    public boolean deleteStaffById(Long staffId);
    
    public Staff updateStaff(Staff updatedStaff);
    
    public Staff addNewStaff(Staff staff, Long restaurantId);
}
