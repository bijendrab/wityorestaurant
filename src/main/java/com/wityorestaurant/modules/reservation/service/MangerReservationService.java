package com.wityorestaurant.modules.reservation.service;

import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.reservation.dto.ReservationDetailsDto;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.model.TimeSpan;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface MangerReservationService {
    Reservation reserveResult(ReservationDetailsDto r,Long restId);


    Reservation processRequest(ReservationDetailsDto r,Long restId);

    RestTable isAvailable
            (LocalDate date,
             List<RestTable> fittingTables,
             TimeSpan reqTimeSpan, int tableNumber, Long restId);

    Integer isTableAssigned(CustomerInfoDTO custInfo,Long restId);
}
