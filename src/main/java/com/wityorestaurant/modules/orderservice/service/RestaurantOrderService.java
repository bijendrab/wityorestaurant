package com.wityorestaurant.modules.orderservice.service;

import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.reservation.dto.ReservationDetailsDto;

public interface RestaurantOrderService {

	public Order placeOrder(ReservationDetailsDto reservation);
	
}
