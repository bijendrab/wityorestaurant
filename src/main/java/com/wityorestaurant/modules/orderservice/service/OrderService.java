package com.wityorestaurant.modules.orderservice.service;

import com.wityorestaurant.modules.customerdata.CustomerOrderDTO;
import com.wityorestaurant.modules.orderservice.model.Order;

public interface OrderService {
    Order processOrderRequest(CustomerOrderDTO customerCheckoutItems,Long restId);
}
