package com.wityorestaurant.modules.reservation.service.impl;

import com.google.gson.Gson;
import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.model.TimeSpan;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;
import com.wityorestaurant.modules.reservation.service.ReservationService;
import com.wityorestaurant.modules.restaurant.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;


@Service(value = "ReservationService")
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RestaurantUserRepository userRepository;

    public Reservation saveReserve
            (RestTable table,
             CustomerInfoDTO customer,
             TimeSpan ts,
             LocalDate submissionDate,
             LocalDate reservationDate,
             String otherReq) {


        Reservation res = new Reservation();
        res.setRelatedTable(table);
        res.setCustomerInfo(new Gson().toJson(customer));
        res.setReservationTime(ts);
        res.setSubmissionDate(submissionDate);
        res.setReservationDate(reservationDate);
        res.setOtherRequirements(otherReq);

        reservationRepository.save(res);

        return res;
    }

}
