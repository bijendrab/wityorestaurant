package com.wityorestaurant.modules.orderservice.service;

import com.wityorestaurant.modules.orderservice.dto.RestaurantOrderDTO;
import com.wityorestaurant.modules.orderservice.dto.UpdateOrderItemDTO;
import com.wityorestaurant.modules.orderservice.model.Order;

public interface RestaurantOrderService {

	public Order placeOrder(RestaurantOrderDTO orderDTO, Long tableId);
	public Boolean removePlacedOrderItem(UpdateOrderItemDTO dto, Long restaurantId, Long orderId);
	
}
