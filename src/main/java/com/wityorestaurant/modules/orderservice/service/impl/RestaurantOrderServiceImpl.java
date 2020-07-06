package com.wityorestaurant.modules.orderservice.service.impl;

import javax.transaction.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.cart.model.RestaurantCartAddOnItems;
import com.wityorestaurant.modules.cart.model.RestaurantCartItem;
import com.wityorestaurant.modules.cart.repository.CartItemRepository;
import com.wityorestaurant.modules.cart.repository.CartRepository;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.model.ProductQuantityOptions;
import com.wityorestaurant.modules.orderservice.dto.RestaurantOrderDTO;
import com.wityorestaurant.modules.orderservice.dto.UpdateOrderItemDTO;
import com.wityorestaurant.modules.orderservice.model.CancelledOrderItem;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderHistory;
import com.wityorestaurant.modules.orderservice.model.OrderItem;
import com.wityorestaurant.modules.orderservice.model.OrderItemAddOn;
import com.wityorestaurant.modules.orderservice.model.OrderStatus;
import com.wityorestaurant.modules.orderservice.repository.CancelledOrderRepository;
import com.wityorestaurant.modules.orderservice.repository.OrderHistoryRepository;
import com.wityorestaurant.modules.orderservice.repository.OrderItemRepository;
import com.wityorestaurant.modules.orderservice.repository.OrderRepository;
import com.wityorestaurant.modules.orderservice.service.OrderQueueService;
import com.wityorestaurant.modules.orderservice.service.RestaurantOrderService;
import com.wityorestaurant.modules.payment.dto.BillingDetailResponse;
import com.wityorestaurant.modules.payment.service.PaymentService;
import com.wityorestaurant.modules.reservation.dto.CheckReservationResponseDTO;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.model.TimeSpan;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;
import com.wityorestaurant.modules.reservation.service.MangerReservationService;
import com.wityorestaurant.modules.reservation.service.ReservationService;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
public class RestaurantOrderServiceImpl implements RestaurantOrderService {

    Logger logger = LoggerFactory.getLogger(RestaurantOrderServiceImpl.class);
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RestTableRepository tableRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CancelledOrderRepository cancelItemRepository;
    @Autowired
    private OrderQueueService orderQueueService;
    @Autowired
    private RestTableRepository restTableRepository;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private MangerReservationService mangerReservationService;

    @Override
    public Order placeOrder(RestaurantOrderDTO orderDTO, Long tableId, RestaurantDetails restaurant) {
        try {
            CustomerInfoDTO customer = new CustomerInfoDTO();
            customer.setCustomerId(restaurant.getRestId());
            customer.setEmailId(restaurant.getEmail());
            customer.setFirstName("Restaurant");
            customer.setLastName("Manager");
            customer.setPhoneNumber(restaurant.getPhone());
            orderDTO.setCustomer(customer);

            List<RestTable> tables = tableRepository.findByRestaurantId(orderDTO.getCustomer().getCustomerId());
            RestTable selectedTable = null;
            for (RestTable tbl : tables) {
                if (tableId == tbl.getId()) {
                    selectedTable = tbl;
                }
            }
            Order newOrder = new Order();
            CheckReservationResponseDTO response = mangerReservationService.isTableAssigned(orderDTO.getCustomer(), restaurant.getRestId());
            if (response.getReservationStatus() == Boolean.FALSE) {
                Reservation reservation = new Reservation();
                reservation.setCustomerInfo(new Gson().toJson(orderDTO.getCustomer()));
                reservation.setRelatedTable(selectedTable);
                reservation.setReservationDate(LocalDate.now());
                String startTime = String.valueOf(LocalDateTime.now()).substring(11, 16);
                String endTime = String.valueOf(LocalDateTime.now().plusHours(2)).substring(11, 16);
                reservation.setReservationTime(new TimeSpan(startTime, endTime));
                reservation.setSubmissionDate(LocalDate.now());
                newOrder.setAccordingReservation(reservationRepository.save(reservation));
            } else {
                newOrder.setAccordingReservation(reservationRepository.save(reservationRepository.getByCustomerId(new Gson().toJson(orderDTO.getCustomer()), restaurant.getRestId())));
            }
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
                for (RestaurantCartAddOnItems restaurantCartAddOnItems : mi.getRestaurantCartAddOnItems()) {
                    String orderAddOnItemUUID = UUID.randomUUID().toString();
                    orderAddOnItemUUID = orderAddOnItemUUID.replaceAll("-", "");
                    OrderItemAddOn orderItemAddOnItem = new OrderItemAddOn();
                    orderItemAddOnItem.setOrderItemAddOnId(orderAddOnItemUUID);
                    orderItemAddOnItem.setItemId(restaurantCartAddOnItems.getItemId());
                    orderItemAddOnItem.setItemName(restaurantCartAddOnItems.getItemName());
                    orderItemAddOnItem.setPrice(restaurantCartAddOnItems.getPrice());
                    orderItemAddOnItem.setOrderItem(menuItem_Order);
                    menuItem_Order.getOrderItemAddOns().add(orderItemAddOnItem);
                }

            }
            newOrder.setTotalCost(totalPrice);
            newOrder.setOrderedBy("restaurant");
            String orderUUID = UUID.randomUUID().toString();
            orderUUID = orderUUID.replaceAll("-", "");
            newOrder.setOrderId(orderUUID);
            orderRepository.save(newOrder);
            orderQueueService.processingOrderToQueue(newOrder, restaurant.getRestId());
            cartItemRepository.deleteAll(orderDTO.getCartItems());
            return newOrder;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    private boolean cancelledOrderItemLogger(Order order,
                                             UpdateOrderItemDTO dto,
                                             Long restaurantId,
                                             String orderItemId) {
        try {
            CancelledOrderItem orderItem = new CancelledOrderItem();
            CustomerInfoDTO customer = dto.getCustomer();
            if (customer.getFirstName().equalsIgnoreCase("restaurant") && customer.getLastName().equalsIgnoreCase("manager")) {
                orderItem.setIsCustomerOrder(Boolean.FALSE);
                orderItem.setCustomerId(null);
                orderItem.setRestaurantId(customer.getCustomerId());
                orderItem.setIsRestaurantOrder(Boolean.TRUE);
            } else {
                orderItem.setIsCustomerOrder(Boolean.TRUE);
                orderItem.setCustomerId(customer.getCustomerId());
                orderItem.setRestaurantId(null);
                orderItem.setIsRestaurantOrder(Boolean.FALSE);
            }
            orderItem.setRestaurantId(restaurantId);
            orderItem.setCancelledOrderItemId(order.getOrderId());
            orderItem.setOrderItemId(orderItemId);
            orderItem.setCancellationTime(LocalDateTime.now());
            cancelItemRepository.save(orderItem);
            return true;
        } catch (Exception e) {
            logger.debug("Unable to save in cancelled order items");
            logger.error("Exception in cancelledOrderLogger==>{}", e);
        }
        return false;
    }

    public Boolean removePlacedOrderItem(UpdateOrderItemDTO dto, Long restaurantId, Long orderId) {
        try {
            Order order = orderRepository.findById(orderId).get();
            OrderItem orderItem = null;
            for (OrderItem orderItem2 : order.getMenuItemOrders()) {
                if (orderItem2.getOrderItemId().equals(dto.getOrderItemId())) {
                    orderItem = orderItem2;
                    break;
                }
            }
            order.setTotalCost(order.getTotalCost() - (float) orderItem.getPrice());
            order.getMenuItemOrders().remove(orderItem);
            orderRepository.save(order);
            if (order.getTotalCost() == 0) {
                orderRepository.deleteOrderById(order.getOrderId());
                if (orderRepository.getOrderByTable(order.getAccordingReservation().getRelatedTable().getId(), restaurantId).isEmpty()) {
                    reservationRepository.deleteReservationById(order.getAccordingReservation().getId());
                    return true;
                }
                return true;
            }
            orderQueueService.updatingOrderToQueue(orderItem, restaurantId);
            cancelledOrderItemLogger(order, dto, restaurantId, orderItem.getOrderItemId());
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
            for (OrderItem item : order.getMenuItemOrders()) {
                if (dto.getOrderItemId().equals(item.getOrderItemId())) {
                    orderItemToBeUpdated = item;
                    break;
                }
            }

            order.setTotalCost(order.getTotalCost() - (float) orderItemToBeUpdated.getPrice());
            order.getMenuItemOrders().remove(orderItemToBeUpdated);

            if (dto.getQuantityOption().equals(orderItemToBeUpdated.getQuantityOption())) {
                double perItemCost = orderItemToBeUpdated.getPrice() / orderItemToBeUpdated.getQuantity();
                double updatedOrderItemCost = perItemCost * dto.getQuantity();
                orderItemToBeUpdated.setQuantity(dto.getQuantity());
                orderItemToBeUpdated.setPrice(updatedOrderItemCost);
                order.getMenuItemOrders().add(orderItemToBeUpdated);
                order.setTotalCost(order.getTotalCost() + (float) updatedOrderItemCost);
                return orderRepository.save(order);
            } else {
                RestaurantDetails restaurant = restaurantRepository.findById(restaurantId).get();
                Product product = null;
                for (Product prod : restaurant.getProduct()) {
                    if (prod.getProductName().equals(orderItemToBeUpdated.getItemName())) {
                        product = prod;
                        break;
                    }
                }
                double updatedOrderItemCost = 0;
                for (ProductQuantityOptions pqo : product.getProductQuantityOptions()) {
                    if (dto.getQuantityOption().equals(pqo.getQuantityOption())) {
                        updatedOrderItemCost = pqo.getPrice() * dto.getQuantity();
                        orderItemToBeUpdated.setPrice(updatedOrderItemCost);
                        orderItemToBeUpdated.setQuantityOption(dto.getQuantityOption());
                        orderItemToBeUpdated.setQuantity(dto.getQuantity());
                        order.getMenuItemOrders().add(orderItemToBeUpdated);
                        order.setTotalCost(order.getTotalCost() + (float) updatedOrderItemCost);
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
        for (OrderItem temp : order.getMenuItemOrders()) {
            if (temp.getOrderItemId().equals(orderItem.getOrderItemId())) {
                orderItemToBeUpdated = temp;
                break;
            }
        }
        if (orderItemToBeUpdated == null) {
            return null;
        } else {
            if (orderItem.getSpecialDiscount() == true) {
                order.getMenuItemOrders().remove(orderItemToBeUpdated);
                if (orderItemToBeUpdated.getSpecialDiscount() == true) {
                    float currentCost = ((float) orderItemToBeUpdated.getPrice() * ((100 - orderItemToBeUpdated.getSpecialDiscountValue()) / 100));
                    order.setTotalCost(order.getTotalCost() - currentCost);
                    orderItemToBeUpdated.setSpecialDiscountValue(orderItem.getSpecialDiscountValue());
                    order.getMenuItemOrders().add(orderItemToBeUpdated);
                    currentCost = ((float) orderItemToBeUpdated.getPrice() * ((100 - orderItemToBeUpdated.getSpecialDiscountValue()) / 100));
                    order.setTotalCost(order.getTotalCost() + currentCost);
                } else {
                    order.setTotalCost(order.getTotalCost() - (float) orderItem.getPrice());
                    orderItemToBeUpdated.setSpecialDiscount(true);
                    orderItemToBeUpdated.setSpecialDiscountValue(orderItem.getSpecialDiscountValue());
                    order.getMenuItemOrders().add(orderItemToBeUpdated);
                    float latestPrice = ((float) orderItemToBeUpdated.getPrice() * ((100 - orderItemToBeUpdated.getSpecialDiscountValue()) / 100));
                    order.setTotalCost(order.getTotalCost() + latestPrice);
                }
            } else {
                order.getMenuItemOrders().remove(orderItemToBeUpdated);
                order.setTotalCost(order.getTotalCost() - ((float) orderItemToBeUpdated.getPrice() * (orderItemToBeUpdated.getSpecialDiscountValue() / 100)));
                orderItemToBeUpdated.setSpecialDiscount(Boolean.FALSE);
                orderItemToBeUpdated.setSpecialDiscountValue(0F);
                order.getMenuItemOrders().add(orderItemToBeUpdated);
                order.setTotalCost(order.getTotalCost() + (float) orderItemToBeUpdated.getPrice());
            }
            logger.debug("Order ITEM UPDATED");
            orderRepository.save(order);
            return orderItemRepository.save(orderItemToBeUpdated);
        }
    }

    public Boolean saveToOrderHistory(Long restId, Long tableId) {
        try {
            RestTable restTable = restTableRepository.findByRestaurantIdAndTableId(tableId, restId);
            List<Reservation> reservations = restTable.getReservationList();
            List<Order> orders = orderRepository.getOrderByTable(tableId, restId);
            saveOrdersHistory(restTable, orders);
            resetTableConfig(restId,tableId);
            return DeleteTableOrderWithReservation(restId, tableId, reservations, orders);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public List<OrderHistory> getOrderHistory(Long restId, Long tableId,int duration , String startDateTime , String endDateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
            List<OrderHistory> orderHistories=orderHistoryRepository.getOrderTableHistory(tableId,restId);
            if(duration==0){
                if (!startDateTime.isEmpty() && !endDateTime.isEmpty()){
                    LocalDateTime startDateTimeParsed = LocalDateTime.parse(startDateTime, formatter);
                    LocalDateTime endDateTimeParsed = LocalDateTime.parse(endDateTime, formatter);
                    List<OrderHistory> orderTableHistoriesWithTimeDiff=orderHistoryRepository.getOrderTableHistoryWithStartAndEnd(tableId,restId,startDateTimeParsed,endDateTimeParsed);
                    return orderTableHistoriesWithTimeDiff;
                }
                else if (!startDateTime.isEmpty() && endDateTime.isEmpty()){
                    LocalDateTime startDateTimeParsed = LocalDateTime.parse(startDateTime, formatter);
                    List<OrderHistory> orderTableHistoriesWithTimeStart=orderHistoryRepository.getOrderTableHistoryWithStart(tableId,restId,startDateTimeParsed);
                    return orderTableHistoriesWithTimeStart;
                }
                return orderHistories;
            }
            if (duration==-1 && tableId==0L){
                if (!startDateTime.isEmpty() && !endDateTime.isEmpty()){
                    LocalDateTime startDateTimeParsed = LocalDateTime.parse(startDateTime, formatter);
                    LocalDateTime endDateTimeParsed = LocalDateTime.parse(endDateTime, formatter);
                    List<OrderHistory> orderRestaurantHistoriesWithTimeDiff=orderHistoryRepository.getOrderRestaurantHistoryWithStartAndEnd(restId,startDateTimeParsed,endDateTimeParsed);
                    return orderRestaurantHistoriesWithTimeDiff;
                }
                else if (!startDateTime.isEmpty() && endDateTime.isEmpty()){
                    LocalDateTime startDateTimeParsed = LocalDateTime.parse(startDateTime, formatter);
                    List<OrderHistory> orderRestaurantHistoriesWithTimeStart=orderHistoryRepository.getOrderRestaurantHistoryWithStart(restId,startDateTimeParsed);
                    return orderRestaurantHistoriesWithTimeStart;
                }
                return orderHistoryRepository.getOrderRestaurantHistory(restId);
            }
            for(Iterator<OrderHistory> orderHistoryIterator=orderHistories.iterator();orderHistoryIterator.hasNext();){
                OrderHistory orderHistory=orderHistoryIterator.next();
                if(orderHistory.getOrderHistoryTime().compareTo(LocalDateTime.now().minusMinutes(duration))<0){
                    orderHistoryIterator.remove();
                }
            }
            return orderHistories;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }



    private void saveOrdersHistory(RestTable restTable, List<Order> orders) throws JsonProcessingException {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrders(new ObjectMapper().writeValueAsString(orders));
        orderHistory.setRestaurantDetails(restTable.getRestaurantDetails());
        orderHistory.setTableId(restTable.getId());
        orderHistory.setPaymentStatus(Boolean.FALSE);
        orderHistory.setPaymentMethod("TBD");
        orderHistory.setOrderCreationTime(orders.get(0).getMenuItemOrders().iterator().next().getOrderCreationTime());Date creationDate = new Date();
        orderHistory.setOrderHistoryTime(LocalDateTime.now());
        orderHistory.setTotalCost(paymentService.getOrderPaymentSummary(restTable.getRestaurantDetails().getRestId(),restTable.getId()).getTotalCost());
        orderHistoryRepository.save(orderHistory);
    }

    private Boolean DeleteTableOrderWithReservation(Long restId, Long tableId, List<Reservation> reservations, List<Order> orders) {
        for (Iterator<Order> orderIterator = orders.iterator(); orderIterator.hasNext();){
            Order order=orderIterator.next();
            for (Iterator<OrderItem> orderItemIterator = order.getMenuItemOrders().iterator(); orderItemIterator.hasNext();) {
                OrderItem orderItem = orderItemIterator.next();
                for (Iterator<OrderItemAddOn> orderItemAddOnIterator = orderItem.getOrderItemAddOns().iterator(); orderItemAddOnIterator.hasNext();) {
                    orderItemAddOnIterator.next();
                    orderItemAddOnIterator.remove();
                }

                orderItemIterator.remove();
            }
            orderRepository.save(order);
            orderRepository.deleteOrderById(order.getOrderId());
        }

        int count=5;
        while (count >0) {
            if(orderRepository.getOrderByTable(tableId,restId).isEmpty()) {
                reservations.forEach(reservation -> {
                    reservationRepository.deleteReservationById(reservation.getId());
                });
                break;
            }
            count--;
        }
        return true;
    }

    public void resetTableConfig(Long restId, Long tableId){
        RestTable restTable = restTableRepository.findByRestaurantIdAndTableId(tableId, restId);
        restTable.setOverAllDiscountEnabled(false);
        restTable.setOverallDiscount(0.0F);
        restTable.setPackagingCharge(0.0F);
        restTable.setPackagingChargeEnabled(false);
        restTable.setServiceChargeEnabled(false);
        restTableRepository.save(restTable);
    }



}
