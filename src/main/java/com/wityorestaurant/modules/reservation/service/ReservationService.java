package com.wityorestaurant.modules.reservation.service;

import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.model.TimeSpan;

import java.sql.Date;

public interface ReservationService {
    Reservation saveReserve(RestTable table,
                            CustomerInfoDTO customer,
                            TimeSpan ts,
                            Date submissionDate,
                            Date reservationDate,
                            String otherReq);

}
