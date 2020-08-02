package com.wityorestaurant.modules.config.service;

import com.wityorestaurant.modules.config.dto.ConfigurationDTO;
import com.wityorestaurant.modules.config.dto.PaymentModeDTO;
import com.wityorestaurant.modules.config.dto.RestTableDTO;
import com.wityorestaurant.modules.config.model.PaymentMode;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.config.model.Staff;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

import java.util.HashMap;
import java.util.List;

public interface RestaurantConfigurationService {
    HashMap<String, Object> getConfig();

    Object add(ConfigurationDTO config);

    Object addTable(RestTableDTO tableConfig);

    PaymentMode addPaymentMethod(PaymentModeDTO paymentModeDTO, RestaurantDetails restaurantDetails);

    List<PaymentMode> getPaymentMethod(Long restaurantId);

    Boolean deletePaymentMethod(Long paymentId,Long restaurantId);

    Boolean setPaymentMethodStatus(Long paymentId,Long restaurantId);

    List<RestTable> getAllTables();
    
    Boolean deleteTableById(Long tableId);

    List<Staff> getAllStaffs();

    List<Staff> getCustomStaffs();

    boolean deleteStaffById(Long staffId);

    Staff updateStaff(Staff updatedStaff);

    Staff addNewStaff(Staff staff, Long restaurantId);

    RestTable updateTableCharges(RestTable dtoTable, Long restId);
    
    public RestTable updateTableById(Long tableId, Long restId, RestTable table);
}
