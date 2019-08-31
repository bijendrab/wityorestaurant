package com.wityorestaurant.modules.orderservice.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.wityorestaurant.modules.customerdata.CustomerCartItems;
import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.customerdata.CustomerOrderDTO;
import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.model.ProductQuantityOptions;
import com.wityorestaurant.modules.orderservice.dto.TableOrdersResponse;
import com.wityorestaurant.modules.orderservice.dto.UpdateOrderItemDTO;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderItem;
import com.wityorestaurant.modules.orderservice.model.OrderStatus;
import com.wityorestaurant.modules.orderservice.repository.OrderRepository;
import com.wityorestaurant.modules.orderservice.service.OrderService;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.repository.RestaurantRepository;

@Service(value = "OrderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    
    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public Order processOrderRequest(CustomerOrderDTO customerCheckoutItems,Long restId) {
        //List<CustomerCartItems> customerCartItems = new Gson().fromJson(customerCheckoutItems.getCustomerCartItems(), CustomerCartItems.class);
        //CustomerInfoDTO customerInfoDTO=new Gson().fromJson(customerCheckoutItems.getCustomerInfoDTO(),CustomerInfoDTO.class);
        Reservation accordingReservation = reservationRepository.getByCustomerId(new Gson().toJson(customerCheckoutItems.getCustomer()),restId);

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
            menuItem_Order.setStatus(OrderStatus.UNPROCESSED.toString());
            menuItem_Order.setQuantityOption(mi.getQuantityOption());
            menuItem_Order.setCustomerCartItems(new Gson().toJson(mi));
            menuItem_Order.setImmediateStatus(mi.getImmediateStatus());
            newOrder.getMenuItemOrders().add(menuItem_Order);
        }

        newOrder.setTotalCost(totalPrice);
        newOrder.setOrderedBy("customer");
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
    public Order getCustomerOrderDetails(CustomerInfoDTO customerInfoDTO, Long restId){
        return orderRepository.getOrderByCustomer(new Gson().toJson(customerInfoDTO),restId);
    }
    public TableOrdersResponse getTableOrderDetails(CustomerInfoDTO customerInfoDTO, Long restId){
        Reservation accordingReservation = reservationRepository.getByCustomerId(new Gson().toJson(customerInfoDTO),restId);
        TableOrdersResponse response = new TableOrdersResponse();
         response.setTableOrders(orderRepository.getOrderByTable(accordingReservation.getRelatedTable().getId(),restId));
         return response;
    }
    
    public TableOrdersResponse getRestaurantTableOrders(Long tableId, Long restaurantId) {
    	TableOrdersResponse response = new TableOrdersResponse();
        response.setTableOrders(orderRepository.getOrderByTable(tableId,restaurantId));
        return response;
    }
    
    public List<Order> getAllTableOrderDetails(Long restId){
        return orderRepository.getAllOrderByRestaurant(restId);
    }
    
    public Order editOrder(UpdateOrderItemDTO dto, Long restaurantId) {
    	try {
    		Order order = orderRepository.getOrderByCustomer(new Gson().toJson(dto.getCustomer()), restaurantId);
    		OrderItem orderItemToBeUpdated = null;
    		for(OrderItem item: order.getMenuItemOrders()) {
    			if(dto.getOrderItemId().equals(item.getOrderItemId())) {
    				orderItemToBeUpdated = item;
    				break;
    			}
    		}
    		
    		order.setTotalCost(order.getTotalCost() - (float)orderItemToBeUpdated.getPrice());
    		order.getMenuItemOrders().remove(orderItemToBeUpdated);
    		
    		if(dto.getQuantityOption().equals(orderItemToBeUpdated.getQuantityOption())) {
    			double perItemCost = orderItemToBeUpdated.getPrice()/orderItemToBeUpdated.getQuantity();
    			double updatedOrderItemCost = perItemCost * dto.getQuantity();
    			orderItemToBeUpdated.setQuantity(dto.getQuantity());
    			orderItemToBeUpdated.setPrice(updatedOrderItemCost);
    			order.getMenuItemOrders().add(orderItemToBeUpdated);
    			order.setTotalCost(order.getTotalCost() + (float)updatedOrderItemCost);
   				return orderRepository.save(order);
   			} else {
   				RestaurantDetails restaurant = restaurantRepository.findById(restaurantId).get();
   				Product product = null;
    			for(Product prod: restaurant.getProduct()) {
    				if(prod.getProductName().equals(orderItemToBeUpdated.getItemName())) {
    					product = prod;
    					break;
    				}
    			}
    			orderItemToBeUpdated.setQuantityOption(dto.getQuantityOption());
    			double updatedOrderItemCost = 0;
    			for(ProductQuantityOptions pqo: product.getProductQuantityOptions()) {
    				if(dto.getQuantityOption().equals(pqo.getQuantityOption())) {
    					updatedOrderItemCost = pqo.getPrice() * dto.getQuantity();
    					orderItemToBeUpdated.setPrice(updatedOrderItemCost);
    					order.getMenuItemOrders().add(orderItemToBeUpdated);
    					order.setTotalCost(order.getTotalCost() + (float)updatedOrderItemCost);
    	   				return orderRepository.save(order);
    				}
    			}
    		}    		
		} catch (Exception e) {
			logger.error("Exception in OrderServiceImpl, method: editOrder --> {}", e.getMessage());
			logger.debug("Stacktrace===> {}", e);
		}
    	return null;
    }
    
    public Boolean removePlacedOrderItem(UpdateOrderItemDTO dto, Long restaurantId) {
    	try {
        	Order order = orderRepository.getOrderByCustomer(new Gson().toJson(dto.getCustomer()), restaurantId);
        	OrderItem orderItem = null;
        	for(OrderItem orderItem2: order.getMenuItemOrders()) {
        		if(orderItem2.getOrderItemId().equals(dto.getOrderItemId())) {
        			orderItem = orderItem2;
        			break;
        		}
        	}
        	order.setTotalCost(order.getTotalCost() - (float)orderItem.getPrice());
        	order.getMenuItemOrders().remove(orderItem);
        	orderRepository.save(order);
        	return true;
		} catch (Exception e) {
			logger.error("Exception in OrderServiceImpl, method: removePlacedOrderItem --> {}", e.getMessage());
			logger.debug("Stacktrace===> {}", e);
		}
    	return false;

    	
    }

}
