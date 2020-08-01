package com.wityorestaurant.modules.orderservice.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import com.google.gson.Gson;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.customerdata.CustomerCartAddOnItems;
import com.wityorestaurant.modules.customerdata.CustomerCartItems;
import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.customerdata.CustomerOrderDTO;
import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.model.ProductQuantityOptions;
import com.wityorestaurant.modules.orderservice.dto.TableOrdersResponse;
import com.wityorestaurant.modules.orderservice.dto.UpdateOrderItemDTO;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderItem;
import com.wityorestaurant.modules.orderservice.model.OrderItemAddOn;
import com.wityorestaurant.modules.orderservice.model.OrderStatus;
import com.wityorestaurant.modules.orderservice.repository.OrderRepository;
import com.wityorestaurant.modules.orderservice.service.OrderQueueService;
import com.wityorestaurant.modules.orderservice.service.OrderService;
import com.wityorestaurant.modules.reservation.dto.CheckReservationResponseDTO;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.reservation.model.TimeSpan;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;
import com.wityorestaurant.modules.reservation.service.MangerReservationService;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.restaurant.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "OrderService")
public class OrderServiceImpl implements OrderService {
    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private OrderQueueService orderQueueService;
    @Autowired
    private RestTableRepository tableRepository;
    @Autowired
    private MangerReservationService mangerReservationService;

    public Order processOrderRequest(CustomerOrderDTO customerOrderDTO, Long restId) {
        try {
            Reservation accordingReservation = reservationRepository.getByCustomerId(new Gson().toJson(customerOrderDTO.getCustomer()), restId);
            if (accordingReservation == null) {
                throw new Exception();
            }

            List<RestTable> tables = tableRepository.findByRestaurantId(restId);
            RestTable selectedTable = null;
            for (RestTable tbl : tables) {
                if (accordingReservation.getRelatedTable().getId() == tbl.getId()) {
                    selectedTable = tbl;
                }
            }
            Order newOrder = new Order();
            CheckReservationResponseDTO response = mangerReservationService.isTableAssigned(customerOrderDTO.getCustomer(), restId);
            if (response.getReservationStatus() == Boolean.FALSE) {
                Reservation reservation = new Reservation();
                reservation.setCustomerInfo(new Gson().toJson(customerOrderDTO.getCustomer()));
                reservation.setRelatedTable(selectedTable);
                reservation.setReservationDate(LocalDate.now());
                String startTime = String.valueOf(LocalDateTime.now()).substring(11, 16);
                String endTime = String.valueOf(LocalDateTime.now().plusHours(2)).substring(11, 16);
                reservation.setReservationTime(new TimeSpan(startTime, endTime));
                reservation.setSubmissionDate(LocalDate.now());
                newOrder.setAccordingReservation(reservationRepository.save(reservation));
            } else {
                newOrder.setAccordingReservation(reservationRepository.save(reservationRepository.getByCustomerId(new Gson().toJson(customerOrderDTO.getCustomer()), restId)));
            }
            newOrder.setStatus(OrderStatus.ON_HOLD);
            float totalPrice = 0;
            Date creationDate = new Date();
            for (CustomerCartItems customerCartItems : customerOrderDTO.getCartItems()) {
                String orderItemUUID = UUID.randomUUID().toString();
                orderItemUUID = orderItemUUID.replaceAll("-", "");
                totalPrice += customerCartItems.getPrice();
                OrderItem menuItem_Order = new OrderItem();
                menuItem_Order.setOrderItemId(orderItemUUID);
                menuItem_Order.setQuantity(customerCartItems.getQuantity());
                menuItem_Order.setItemName(customerCartItems.getItemName());
                menuItem_Order.setPrice(customerCartItems.getPrice());
                menuItem_Order.setOrderCreationTime(creationDate);
                menuItem_Order.setStatus(OrderStatus.UNPROCESSED.toString());
                menuItem_Order.setQuantityOption(customerCartItems.getQuantityOption());
                menuItem_Order.setCustomerCartItems(new Gson().toJson(customerCartItems));
                menuItem_Order.setImmediateStatus(customerCartItems.getImmediateStatus());
                menuItem_Order.setIsVeg(new Gson().fromJson(customerCartItems.getProductJson(), Product.class).getIsVeg());
                menuItem_Order.setOrder(newOrder);
                newOrder.getMenuItemOrders().add(menuItem_Order);
                for (CustomerCartAddOnItems customerCartAddOnItems : customerCartItems.getSelectCartAddOnItems()) {
                    String orderAddOnItemUUID = UUID.randomUUID().toString();
                    orderAddOnItemUUID = orderAddOnItemUUID.replaceAll("-", "");
                    OrderItemAddOn orderItemAddOnItem = new OrderItemAddOn();
                    orderItemAddOnItem.setOrderItemAddOnId(orderAddOnItemUUID);
                    orderItemAddOnItem.setItemId(customerCartAddOnItems.getItemId());
                    orderItemAddOnItem.setItemName(customerCartAddOnItems.getItemName());
                    orderItemAddOnItem.setPrice(customerCartAddOnItems.getPrice());
                    orderItemAddOnItem.setOrderItem(menuItem_Order);
                    menuItem_Order.getOrderItemAddOns().add(orderItemAddOnItem);
                }

            }
            newOrder.setTotalCost(totalPrice);
            newOrder.setOrderedBy("customer");
            String orderUUID = UUID.randomUUID().toString();
            orderUUID = orderUUID.replaceAll("-", "");
            newOrder.setOrderId(orderUUID);
            orderRepository.save(newOrder);
            orderQueueService.processingOrderToQueue(newOrder, restId);
            return newOrder;
        }
        catch (Exception e) {
            logger.error("Exception in OrderServiceImpl, method: place order --> {}", e.getMessage());
            logger.debug("Stacktrace===> {}", e);
        }
        return null;
    }

    public void setOrder(Order order) {
        Set<OrderItem> pqo = new HashSet<>();
        for (OrderItem potion : order.getMenuItemOrders()) {
            potion.setOrder(order);
            pqo.add(potion);
        }
    }

    public List<Order> getCustomerOrderDetails(CustomerInfoDTO customerInfoDTO, Long restId) {
        return orderRepository.getOrderByCustomer(new Gson().toJson(customerInfoDTO), restId);
    }

    public TableOrdersResponse getTableOrderDetails(CustomerInfoDTO customerInfoDTO, Long restId) {
        try {
            Reservation accordingReservation = reservationRepository.getByCustomerId(new Gson().toJson(customerInfoDTO), restId);
            if (accordingReservation == null) {
                return new TableOrdersResponse();
            }
            TableOrdersResponse response = new TableOrdersResponse();
            response.setTableOrders(orderRepository.getOrderByTable(accordingReservation.getRelatedTable().getId(), restId));
            return response;
        }
        catch (Exception e) {
            logger.error("Exception in OrderServiceImpl, method: place order --> {}", e.getMessage());
            logger.debug("Stacktrace===> {}", e);
            return new TableOrdersResponse();
        }
    }

    public TableOrdersResponse getRestaurantTableOrders(Long tableId, Long restaurantId) {
        TableOrdersResponse response = new TableOrdersResponse();
        List<Order> orderList = orderRepository.getOrderByTable(tableId, restaurantId);
        response.setTableOrders(orderList);
        return response;
    }

    public List<Order> getAllTableOrderDetails(Long restId) {
        return orderRepository.getAllOrderByRestaurant(restId);
    }

    public Order editOrder(UpdateOrderItemDTO dto, Long restaurantId) {
        try {
            List<Order> order = orderRepository.getOrderByCustomer(new Gson().toJson(dto.getCustomer()), restaurantId);
            OrderItem orderItemToBeUpdated = null;
            Order newOrder = null;
            for (Order orderItr : order) {
                for (OrderItem item : orderItr.getMenuItemOrders()) {
                    if (dto.getOrderItemId().equals(item.getOrderItemId())) {
                        orderItemToBeUpdated = item;
                        newOrder = orderItr;
                        break;
                    }
                }
            }

            newOrder.setTotalCost(newOrder.getTotalCost() - (float) orderItemToBeUpdated.getPrice());
            newOrder.getMenuItemOrders().remove(orderItemToBeUpdated);

            if (dto.getQuantityOption().equals(orderItemToBeUpdated.getQuantityOption())) {
                double perItemCost = orderItemToBeUpdated.getPrice() / orderItemToBeUpdated.getQuantity();
                double updatedOrderItemCost = perItemCost * dto.getQuantity();
                orderItemToBeUpdated.setQuantity(dto.getQuantity());
                orderItemToBeUpdated.setPrice(updatedOrderItemCost);
                newOrder.getMenuItemOrders().add(orderItemToBeUpdated);
                newOrder.setTotalCost(newOrder.getTotalCost() + (float) updatedOrderItemCost);
                return orderRepository.save(newOrder);
            } else {
                RestaurantDetails restaurant = restaurantRepository.findById(restaurantId).get();
                Product product = null;
                for (Product prod : restaurant.getProduct()) {
                    if (prod.getProductName().equals(orderItemToBeUpdated.getItemName())) {
                        product = prod;
                        break;
                    }
                }
                orderItemToBeUpdated.setQuantityOption(dto.getQuantityOption());
                double updatedOrderItemCost = 0;
                for (ProductQuantityOptions pqo : product.getProductQuantityOptions()) {
                    if (dto.getQuantityOption().equals(pqo.getQuantityOption())) {
                        updatedOrderItemCost = pqo.getPrice() * dto.getQuantity();
                        orderItemToBeUpdated.setPrice(updatedOrderItemCost);
                        newOrder.getMenuItemOrders().add(orderItemToBeUpdated);
                        newOrder.setTotalCost(newOrder.getTotalCost() + (float) updatedOrderItemCost);
                        return orderRepository.save(newOrder);
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
            List<Order> order = orderRepository.getOrderByCustomer(new Gson().toJson(dto.getCustomer()), restaurantId);
            OrderItem orderItem = null;
            Order newOrder = null;
            for (Order orderItr : order) {
                for (OrderItem orderItem2 : orderItr.getMenuItemOrders()) {
                    if (orderItem2.getOrderItemId().equals(dto.getOrderItemId())) {
                        orderItem = orderItem2;
                        newOrder = orderItr;
                        break;
                    }
                }
            }
            newOrder.setTotalCost(newOrder.getTotalCost() - (float) orderItem.getPrice());
            newOrder.getMenuItemOrders().remove(orderItem);
            orderRepository.save(newOrder);
            if (newOrder.getTotalCost() == 0) {
                orderRepository.deleteOrderById(newOrder.getOrderId());
                if (orderRepository.getOrderByTable(newOrder.getAccordingReservation().getRelatedTable().getId(), restaurantId).isEmpty()) {
                    reservationRepository.deleteReservationById(newOrder.getAccordingReservation().getId());
                    return true;
                }
                return true;
            }
            orderQueueService.updatingOrderToQueue(orderItem, restaurantId);
            return true;
        } catch (Exception e) {
            logger.error("Exception in OrderServiceImpl, method: removePlacedOrderItem --> {}", e.getMessage());
            logger.debug("Stacktrace===> {}", e);
        }
        return false;


    }

}
