package com.wityorestaurant.modules.config.service;

import com.wityorestaurant.modules.config.dto.ConfigurationDTO;
import com.wityorestaurant.modules.config.dto.RestTableDTO;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.config.model.Staff;

import java.util.HashMap;
import java.util.List;

public interface RestaurantConfigurationService {
    HashMap<String, Object> getConfig();

    Object add(ConfigurationDTO config);

    Object addTable(RestTableDTO tableConfig);

    List<RestTable> getAllTables();

    List<Staff> getAllStaffs();

    List<Staff> getCustomStaffs();

    boolean deleteStaffById(Long staffId);

    Staff updateStaff(Staff updatedStaff);

    Staff addNewStaff(Staff staff, Long restaurantId);

    RestTable updateTableCharges(RestTable dtoTable, Long restId);
    
    public RestTable updateTableById(Long tableId, Long restId, RestTable table);
}
