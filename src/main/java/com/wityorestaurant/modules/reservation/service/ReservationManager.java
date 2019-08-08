package com.wityorestaurant.modules.reservation.service;

import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.reservation.dto.CheckRequestDTO;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.model.TimeSpan;

import java.sql.Date;
import java.util.List;

public interface ReservationManager {
    Reservation reserveResult(CheckRequestDTO r);


    Reservation processRequest(CheckRequestDTO r);

    RestTable isAvailable
            (Date date,
             List<RestTable> fittingTables,
             TimeSpan reqTimeSpan, int tableNumber);

    Integer isTableAssigned(CustomerInfoDTO custInfo);
}
