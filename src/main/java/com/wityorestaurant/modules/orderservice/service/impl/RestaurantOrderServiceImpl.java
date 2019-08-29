package com.wityorestaurant.modules.orderservice.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.orderservice.dto.RestaurantOrderDTO;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderItem;
import com.wityorestaurant.modules.orderservice.model.OrderStatus;
import com.wityorestaurant.modules.orderservice.repository.OrderRepository;
import com.wityorestaurant.modules.orderservice.service.RestaurantOrderService;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.model.TimeSpan;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;

@Service
public class RestaurantOrderServiceImpl implements RestaurantOrderService{
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private RestTableRepository tableRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order placeOrder(RestaurantOrderDTO orderDTO, Long tableId) {
		List<RestTable> tables = tableRepository.findByRestaurantId(orderDTO.getCustomer().getCustomerId());
		RestTable selectedTable = null; 
		for(RestTable tbl : tables) {
			if(tableId == tbl.getId()) {
				selectedTable = tbl;
			}
		}
		System.out.println(selectedTable.getTableNumber()+" TABLE NUM");
		Reservation reservation = new Reservation();
		System.out.println(reservation.getId()+" reservation NUM");
		reservation.setCustomerInfo(new Gson().toJson(orderDTO.getCustomer()).toString());
		reservation.setRelatedTable(selectedTable);
		reservation.setReservationDate(LocalDate.now());
		String startTime = String.valueOf(LocalDateTime.now()).substring(11,16);
        String endTime = String.valueOf(LocalDateTime.now().plusHours(2)).substring(11,16);
        reservation.setReservationTime(new TimeSpan(startTime, endTime));
        reservation.setSubmissionDate(LocalDate.now());
        
        Order newOrder = new Order();

        newOrder.setAccordingReservation(reservationRepository.save(reservation));
        newOrder.setStatus(OrderStatus.ON_HOLD);
        float totalPrice = 0;
        Date creationDate = new Date();
        for (RestaurantCartItem mi : orderDTO.getCartItems()) {
            String orderItemUUID = UUID.randomUUID().toString();
            orderItemUUID = orderItemUUID.replaceAll("-", "");
            totalPrice += mi.getPrice();
            OrderItem menuItem_Order = new OrderItem();
            menuItem_Order.setOrderItemId(orderItemUUID);
            menuItem_Order.setQuantity(mi.getQuantity());
            menuItem_Order.setItemName(mi.getItemName());
            menuItem_Order.setPrice(mi.getPrice());
            menuItem_Order.setOrderCreationTime(creationDate);
            menuItem_Order.setStatus(OrderStatus.UNPROCESSED.toString());
            menuItem_Order.setQuantityOption(mi.getQuantityOption());
            menuItem_Order.setCustomerCartItems(new Gson().toJson(mi));
            menuItem_Order.setImmediateStatus(mi.getImmediateStatus());
            menuItem_Order.setOrderedBy(mi.getOrderTaker());
            menuItem_Order.setOrder(newOrder);
            newOrder.getMenuItemOrders().add(menuItem_Order);
        }

        newOrder.setTotalCost(totalPrice);
        newOrder.setOrderedBy("restaurant");
        orderRepository.save(newOrder);
        return newOrder;
        
	}

	

}
