package com.wityorestaurant.modules.orderservice.service;

import com.wityorestaurant.modules.customerdata.CustomerCheckoutItems;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.reservation.model.Reservation;

public interface OrderService {
    Order processOrderRequest(CustomerCheckoutItems customerCheckoutItems);
}
