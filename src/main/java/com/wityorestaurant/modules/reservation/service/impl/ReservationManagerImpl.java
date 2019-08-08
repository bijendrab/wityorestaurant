package com.wityorestaurant.modules.reservation.service.impl;

import com.google.gson.Gson;
import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.reservation.dto.CheckRequestDTO;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.model.TimeSpan;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;
import com.wityorestaurant.modules.reservation.service.ReservationManager;
import com.wityorestaurant.modules.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service(value = "ReservationManager")
public class ReservationManagerImpl implements ReservationManager {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RestTableRepository tableDao;

    public Reservation reserveResult
            (CheckRequestDTO r) {

        Reservation resResult = processRequest(r);

        if (resResult != null) {
            System.out.println("reserved !!!");
            System.out.println(resResult.toString());
            return resResult;
        }

        return null;
    }

    public Reservation processRequest
            (CheckRequestDTO r) {

        List<RestTable> fittingTables = tableDao.findAll();

        // check if they are available for the given time
        RestTable availableTable = isAvailable(r.getDate(), fittingTables, r.getTs(),r.getTableNumber());

        if (availableTable != null) {        // reserve it if you can
             /*
                    1- reserve the availableTable
                    2- update the table state
                    3- return true
             */
            Date now = Date.valueOf(LocalDate.now());
            return reservationService.saveReserve(
                    availableTable,
                    r.getCustomerInfo(),
                    r.getTs(),
                    now,
                    r.getDate(),
                    "");

        }
        return null;
    }


    public RestTable isAvailable
            (Date date,
             List<RestTable> fittingTables,
             TimeSpan reqTimeSpan, int tableNumber) {
         /*
            check for each table whether it is available or not:
                return the first one that is not even booked for the given date.

                check for the timespan intersection if the table was booked already:
                    if it doesn't intersect with requested timespan then :
                        return true.
                    else :
                        return false.
         */

        for (RestTable table : fittingTables) {
            /* fetch all the reservation for this table */
            List<Reservation> allResForThisTable = reservationRepository.getByTableId(table.getTableNumber());

            if (allResForThisTable == null || allResForThisTable.size() == 0) {    // if not reserved before
                if(table.getTableNumber()==tableNumber)
                    return table;
            } else {    // if reserved already check for TS intersection
                for (Reservation resv : allResForThisTable) {
                    if (resv.getRelatedTable().getTableNumber()==tableNumber) {
                        return table;
                    }
                }
            }
        }

        //TODO: throw an exceptions
        return null;
    }

    public Integer isTableAssigned(CustomerInfoDTO custInfo) {

        Reservation allResForThisTable = reservationRepository.getByCustomerId(new Gson().toJson(custInfo));

        if (allResForThisTable == null ) {    // if not reserved before
            return 0;
        }
        else{

                return allResForThisTable.getRelatedTable().getTableNumber().intValue();
            }
        }

}


