package com.wityorestaurant.modules.orderservice.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import com.wityorestaurant.modules.cart.repository.CartRepository;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.model.ProductQuantityOptions;
import com.wityorestaurant.modules.orderservice.dto.RestaurantOrderDTO;
import com.wityorestaurant.modules.orderservice.dto.UpdateOrderItemDTO;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderItem;
import com.wityorestaurant.modules.orderservice.model.OrderStatus;
import com.wityorestaurant.modules.orderservice.repository.OrderRepository;
import com.wityorestaurant.modules.orderservice.service.RestaurantOrderService;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.model.TimeSpan;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.repository.RestaurantRepository;

@Service
public class RestaurantOrderServiceImpl implements RestaurantOrderService{
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private RestTableRepository tableRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
    private RestaurantRepository restaurantRepository;

	Logger logger = LoggerFactory.getLogger(RestaurantOrderServiceImpl.class);
	
	@Override
	public Order placeOrder(RestaurantOrderDTO orderDTO, Long tableId, RestaurantDetails restaurant) {
		CustomerInfoDTO customer = new CustomerInfoDTO();
        customer.setCustomerId(restaurant.getRestId());
        customer.setEmailId(restaurant.getEmail());
        customer.setFirstName("Restaurant");
        customer.setLastName("Manager");
        customer.setPhoneNumber(restaurant.getPhone());
        orderDTO.setCustomer(customer);
        
		List<RestTable> tables = tableRepository.findByRestaurantId(orderDTO.getCustomer().getCustomerId());
		RestTable selectedTable = null; 
		for(RestTable tbl : tables) {
			if(tableId == tbl.getId()) {
				selectedTable = tbl;
			}
		}
		Reservation reservation = new Reservation();
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
            menuItem_Order.setOrder(newOrder);
            newOrder.getMenuItemOrders().add(menuItem_Order);
        }
        newOrder.setTotalCost(totalPrice);
        newOrder.setOrderedBy("restaurant");
        
        RestaurantCart cart = restaurant.getCart();
        for(RestaurantCartItem cartItem : cart.getCartItems()) {
        	for(RestaurantCartItem dtoCartItem : orderDTO.getCartItems()) {
        		if(cartItem.getCartItemId() == dtoCartItem.getCartItemId()) {
        			cart.getCartItems().remove(cartItem);
        			cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());
        			break;
        		}
        	}
        }
        
        cartRepository.save(cart);
        orderRepository.save(newOrder);
        return newOrder;
        
	}
	
	public Boolean removePlacedOrderItem(UpdateOrderItemDTO dto, Long restaurantId, Long orderId) {
    	try {
        	Order order = orderRepository.findById(orderId).get();
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
	
	public Order updateOrderedItem(UpdateOrderItemDTO dto, Long restaurantId, Long orderId) {
    	try {
    		Order order = orderRepository.findById(orderId).get();
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
    			double updatedOrderItemCost = 0;
    			for(ProductQuantityOptions pqo: product.getProductQuantityOptions()) {
    				if(dto.getQuantityOption().equals(pqo.getQuantityOption())) {
    					updatedOrderItemCost = pqo.getPrice() * dto.getQuantity();
    					orderItemToBeUpdated.setPrice(updatedOrderItemCost);
    					orderItemToBeUpdated.setQuantityOption(dto.getQuantityOption());
    					orderItemToBeUpdated.setQuantity(dto.getQuantity());
    					order.getMenuItemOrders().add(orderItemToBeUpdated);
    					order.setTotalCost(order.getTotalCost() + (float)updatedOrderItemCost);
    	   				return orderRepository.save(order);
    				}
    			}
    		}    		
		} catch (Exception e) {
			logger.error("Exception in RestaurantOrderServiceImpl, method: updateOrderedItem --> {}", e.getMessage());
			logger.debug("Stacktrace===> {}", e);
		}
    	return null;
    }
	
	public OrderItem updateOrderItemSpecialDiscount(OrderItem orderItem, Long orderId) {
		Order order = orderRepository.findById(orderId).get();
		OrderItem orderItemToBeUpdated = null;
		for(OrderItem temp: order.getMenuItemOrders()) {
			if(temp.getOrderItemId() == orderItem.getOrderItemId()) {
				orderItemToBeUpdated = temp;
				break;
			}
		}
		if(orderItemToBeUpdated == null) {
			return null;
		} else {
			if(orderItem.getSpecialDiscount() == true) {
				order.getMenuItemOrders().remove(orderItemToBeUpdated);
				if(orderItemToBeUpdated.getSpecialDiscount() == true) {
					order.setTotalCost(order.getTotalCost() - ((float)orderItemToBeUpdated.getPrice() * (orderItemToBeUpdated.getSpecialDiscountValue()/100)));
					orderItemToBeUpdated.setSpecialDiscountValue(orderItem.getSpecialDiscountValue());
					order.getMenuItemOrders().add(orderItemToBeUpdated);
					order.setTotalCost(order.getTotalCost() + ((float)orderItemToBeUpdated.getPrice() * (orderItemToBeUpdated.getSpecialDiscountValue()/100)));
				} else {
					order.setTotalCost(order.getTotalCost() - (float)orderItem.getPrice());
					orderItemToBeUpdated.setSpecialDiscount(true);
					orderItemToBeUpdated.setSpecialDiscountValue(orderItem.getSpecialDiscountValue());
					order.getMenuItemOrders().add(orderItemToBeUpdated);
					order.setTotalCost(order.getTotalCost() + ((float)orderItemToBeUpdated.getPrice() * (orderItemToBeUpdated.getSpecialDiscountValue()/100)));
				}
			} else {
				order.getMenuItemOrders().remove(orderItemToBeUpdated);
				order.setTotalCost(order.getTotalCost() - ((float)orderItemToBeUpdated.getPrice() * (orderItemToBeUpdated.getSpecialDiscountValue()/100)));
				orderItemToBeUpdated.setSpecialDiscount(false);
				orderItemToBeUpdated.setSpecialDiscountValue(0F);
				order.getMenuItemOrders().add(orderItemToBeUpdated);
				order.setTotalCost(order.getTotalCost() + (float)orderItemToBeUpdated.getPrice());
			}
			orderRepository.save(order);
			return orderItemToBeUpdated;
		}
	}
	
}