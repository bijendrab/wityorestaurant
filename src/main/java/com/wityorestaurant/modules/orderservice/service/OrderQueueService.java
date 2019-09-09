package com.wityorestaurant.modules.orderservice.service;

import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderItem;

public interface OrderQueueService {
    void processingOrderToQueue(Order newOrder,Long restId);
    void updatingOrderToQueue(OrderItem orderItem, Long restId);
}
