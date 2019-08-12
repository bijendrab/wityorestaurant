package com.wityorestaurant.modules.reservation.dto;

import java.util.List;

import com.wityorestaurant.modules.config.model.RestTable;

public class CheckReservationResponseDTO {
	
	private Boolean reservationStatus;
    private List<RestTable> restaurantTable;
	public Boolean getReservationStatus() {
		return reservationStatus;
	}
	public void setReservationStatus(Boolean reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	public List<RestTable> getRestaurantTable() {
		return restaurantTable;
	}
	public void setRestaurantTable(List<RestTable> restaurantTable) {
		this.restaurantTable = restaurantTable;
	}

}
