package com.wityorestaurant.modules.orderservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.service.RestaurantOrderService;
import com.wityorestaurant.modules.reservation.dto.ReservationDetailsDto;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;
import com.wityorestaurant.modules.reservation.service.ReservationService;

@Service
public class RestaurantOrderServiceImpl implements RestaurantOrderService{
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private RestTableRepository tableRepository;
	
	@Autowired
	private ReservationService reservationService;

	@Override
	public Order placeOrder(ReservationDetailsDto reservation) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
