package com.wityorestaurant.modules.reservation.service;

import java.time.LocalDate;

import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.model.TimeSpan;

public interface ReservationService {
    Reservation saveReserve(RestTable table,
                            CustomerInfoDTO customer,
                            TimeSpan ts,
                            LocalDate submissionDate,
                            LocalDate reservationDate,
                            String otherReq);

}
