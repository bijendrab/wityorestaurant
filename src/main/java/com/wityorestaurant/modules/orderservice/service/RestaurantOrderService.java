package com.wityorestaurant.modules.orderservice.service;

import com.wityorestaurant.modules.customerdata.CustomerOrderDTO;
import com.wityorestaurant.modules.orderservice.model.Order;

public interface RestaurantOrderService {

	Order placeOrder(CustomerOrderDTO orderDTO, Long tableId);
	
}
