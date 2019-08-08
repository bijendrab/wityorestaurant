package com.wityorestaurant.modules.orderservice.service.impl;

import com.google.gson.Gson;
import com.wityorestaurant.modules.customerdata.CustomerCartItems;
import com.wityorestaurant.modules.customerdata.CustomerCheckoutItems;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderItem;
import com.wityorestaurant.modules.orderservice.model.OrderStatus;
import com.wityorestaurant.modules.orderservice.repository.OrderRepository;
import com.wityorestaurant.modules.orderservice.service.OrderService;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "OrderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private OrderRepository orderRepository;

    public Order processOrderRequest(CustomerCheckoutItems customerCheckoutItems) {
        //List<CustomerCartItems> customerCartItems = new Gson().fromJson(customerCheckoutItems.getCustomerCartItems(), CustomerCartItems.class);
        //CustomerInfoDTO customerInfoDTO=new Gson().fromJson(customerCheckoutItems.getCustomerInfoDTO(),CustomerInfoDTO.class);
        Reservation accordingReservation = reservationRepository.getByCustomerId(new Gson().toJson(customerCheckoutItems.getCustomer()));

        if (accordingReservation == null) {
            // TODO: throw not valid reservation
        }


        Order newOrder = new Order();

        newOrder.setAccordingReservation(accordingReservation);
        newOrder.setStatus(OrderStatus.ON_HOLD);
        float totalPrice = 0;
        Date creationDate = new Date();
        for (CustomerCartItems mi : customerCheckoutItems.getCartItems()) {
            String orderItemUUID = UUID.randomUUID().toString();
            orderItemUUID = orderItemUUID.replaceAll("-", "");
            totalPrice += mi.getPrice();
            OrderItem menuItem_Order = new OrderItem();
            menuItem_Order.setOrderItemId(orderItemUUID);
            menuItem_Order.setQuantity(mi.getQuantity());
            menuItem_Order.setItemName(mi.getItemName());
            menuItem_Order.setPrice(mi.getPrice());
            menuItem_Order.setOrderCreationTime(creationDate);
            menuItem_Order.setStatus("Unprocessed");
            menuItem_Order.setQuantityOption(mi.getQuantityOption());
            menuItem_Order.setCustomerCartItems(new Gson().toJson(mi));
            /*menuItem_Order.set(mi.getProduct());
            menuItem_Order.setCart(mi.getCart());
            for (Map<String,Object> x:immediateReq.getCartItems()){
                if ((Integer)(x.get("cartItemId"))==mi.getCartItemId()){
                    menuItem_Order.setImmediateStatus((Boolean)x.get("ImmidiateOption"));
                    break;
                }
            }*/

            newOrder.getMenuItemOrders().add(menuItem_Order);
        }

        newOrder.setTotalCost(totalPrice);
        setOrder(newOrder);
        orderRepository.save(newOrder);
        return newOrder;
    }
    public void setOrder(Order order) {
        Set<OrderItem> pqo = new HashSet<>();
        for (OrderItem potion : order.getMenuItemOrders()) {
            potion.setOrder(order);
            pqo.add(potion);
        }
    }
}