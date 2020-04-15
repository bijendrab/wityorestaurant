package com.wityorestaurant.modules.orderservice.service;

import java.time.LocalDateTime;
import java.util.List;
import com.wityorestaurant.modules.orderservice.dto.RestaurantOrderDTO;
import com.wityorestaurant.modules.orderservice.dto.UpdateOrderItemDTO;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderHistory;
import com.wityorestaurant.modules.orderservice.model.OrderItem;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

public interface RestaurantOrderService {

    Order placeOrder(RestaurantOrderDTO orderDTO, Long tableId, RestaurantDetails restaurant);

    Boolean removePlacedOrderItem(UpdateOrderItemDTO dto, Long restaurantId, Long orderId);

    Order updateOrderedItem(UpdateOrderItemDTO dto, Long restaurantId, Long orderId);

    OrderItem updateOrderItemSpecialDiscount(OrderItem orderItem, Long orderId);

    Boolean saveToOrderHistory(Long restId , Long tableId);

    List<OrderHistory> getOrderHistory(Long restId , Long tableId, int duration, String startDateTime, String endDateTime);

}
