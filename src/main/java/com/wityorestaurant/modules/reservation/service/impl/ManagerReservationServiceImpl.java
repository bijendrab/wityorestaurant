package com.wityorestaurant.modules.reservation.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.reservation.dto.CheckReservationResponseDTO;
import com.wityorestaurant.modules.reservation.dto.ReservationDetailsDto;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.model.TimeSpan;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;
import com.wityorestaurant.modules.reservation.service.MangerReservationService;
import com.wityorestaurant.modules.reservation.service.ReservationService;

@Service(value = "ReservationManager")
public class ManagerReservationServiceImpl implements MangerReservationService {
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private ReservationService reservationService;

	@Autowired
	private RestTableRepository tableRepository;

	public Reservation reserveResult(ReservationDetailsDto r, Long restId) {

		Reservation resResult = processRequest(r, restId);

		if (resResult != null) {
			System.out.println("reserved !!!");
			System.out.println(resResult.toString());
			return resResult;
		}

		return null;
	}

	public Reservation processRequest(ReservationDetailsDto r, Long restId) {

		List<RestTable> fittingTables = tableRepository.findAll();

		// check if they are available for the given time
		RestTable availableTable = isAvailable(r.getDate(), fittingTables, r.getTs(), r.getTableNumber(), restId);

		if (availableTable != null) { // reserve it if you can
			/*
			 * 1- reserve the availableTable 2- update the table state 3- return true
			 */
			LocalDate now = LocalDate.now();
			return reservationService.saveReserve(availableTable, r.getCustomerInfo(), r.getTs(), now, r.getDate(), "");

		}
		return null;
	}

	public RestTable isAvailable(LocalDate date, List<RestTable> fittingTables, TimeSpan reqTimeSpan, int tableNumber,
			Long restId) {
		/*
		 * check for each table whether it is available or not: return the first one
		 * that is not even booked for the given date.
		 * 
		 * check for the timespan intersection if the table was booked already: if it
		 * doesn't intersect with requested timespan then : return true. else : return
		 * false.
		 */

		for (RestTable table : fittingTables) {
			/* fetch all the reservation for this table */
			List<Reservation> allResForThisTable = reservationRepository.getByTableId(table.getId(), restId);
			if (allResForThisTable == null || allResForThisTable.size() == 0) { // if not reserved before
				if (table.getId() == tableNumber)
					return table;
			} else { // if reserved already check for TS intersection
				for (Reservation resv : allResForThisTable) {
					if (resv.getRelatedTable().getId() == tableNumber) {
						return resv.getRelatedTable();
					}
				}
			}
		}
		// TODO: throw an exceptions
		return null;
	}

	public CheckReservationResponseDTO isTableAssigned(CustomerInfoDTO custInfo, Long restId) {
		Reservation allResForThisTable = reservationRepository.getByCustomerId(new Gson().toJson(custInfo), restId);
		List<RestTable> list = new ArrayList<>();
		CheckReservationResponseDTO response = new CheckReservationResponseDTO();
		if (allResForThisTable == null) { // if not reserved before
			List<RestTable> tables = tableRepository.findByRestaurantId(restId);
			response.setReservationStatus(Boolean.FALSE);
			response.setRestaurantTable(tables);
			return response;
		} else {
			response.setReservationStatus(Boolean.TRUE);
			list.add(allResForThisTable.getRelatedTable());
			response.setRestaurantTable(list);
			return response;
		}
	}

}
