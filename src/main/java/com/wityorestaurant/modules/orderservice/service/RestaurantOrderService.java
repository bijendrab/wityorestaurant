package com.wityorestaurant.modules.orderservice.service;

import com.wityorestaurant.modules.orderservice.dto.RestaurantOrderDTO;
import com.wityorestaurant.modules.orderservice.dto.UpdateOrderItemDTO;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderItem;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

public interface RestaurantOrderService {

	public Order placeOrder(RestaurantOrderDTO orderDTO, Long tableId, RestaurantDetails restaurant);
	public Boolean removePlacedOrderItem(UpdateOrderItemDTO dto, Long restaurantId, Long orderId, Long tableId);
	public Order updateOrderedItem(UpdateOrderItemDTO dto, Long restaurantId, Long orderId);
	public OrderItem updateOrderItemSpecialDiscount(OrderItem orderItem, Long orderId);
	
}
