package com.wityorestaurant.modules.orderservice.service;

import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.customerdata.CustomerOrderDTO;
import com.wityorestaurant.modules.orderservice.dto.TableOrdersResponse;
import com.wityorestaurant.modules.orderservice.dto.UpdateOrderItemDTO;
import com.wityorestaurant.modules.orderservice.model.Order;

import java.util.List;

public interface OrderService {
    Order processOrderRequest(CustomerOrderDTO customerCheckoutItems,Long restId);
    Order getCustomerOrderDetails(CustomerInfoDTO customerInfoDTO,Long restId);
    TableOrdersResponse getTableOrderDetails(CustomerInfoDTO customerInfoDTO, Long restId);
    List<Order> getAllTableOrderDetails(Long restId);
    public Order editOrder(UpdateOrderItemDTO dto, Long restaurantId);
    public Boolean removePlacedOrderItem(UpdateOrderItemDTO dto, Long restaurantId);
    public TableOrdersResponse getRestaurantTableOrders(Long tableId, Long restaurantId);
}
