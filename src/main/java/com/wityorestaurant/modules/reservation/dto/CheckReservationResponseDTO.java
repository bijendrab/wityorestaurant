package com.wityorestaurant.modules.reservation.dto;

import java.util.List;

import com.wityorestaurant.modules.config.model.RestTable;

public class CheckReservationResponseDTO {
	
	private Integer reservationStatus;
    private List<RestTable> restaurantTable;
	public Integer getReservationStatus() {
		return reservationStatus;
	}
	public void setReservationStatus(Integer reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	public List<RestTable> getRestaurantTable() {
		return restaurantTable;
	}
	public void setRestaurantTable(List<RestTable> restaurantTable) {
		this.restaurantTable = restaurantTable;
	}

}
