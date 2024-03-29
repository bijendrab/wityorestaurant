package com.wityorestaurant.modules.orderservice.service.impl;

import java.util.List;
import com.google.gson.Gson;
import com.wityorestaurant.modules.config.model.Category;
import com.wityorestaurant.modules.config.repository.CategoryRepository;
import com.wityorestaurant.modules.config.repository.CuisineRepository;
import com.wityorestaurant.modules.config.repository.SubCategoryRepository;
import com.wityorestaurant.modules.customerdata.CustomerCartItems;
import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderItem;
import com.wityorestaurant.modules.orderservice.model.OrderQueue;
import com.wityorestaurant.modules.orderservice.repository.OrderQueueRepository;
import com.wityorestaurant.modules.orderservice.service.OrderQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "OrderQueueService")
public class OrderQueueServiceImpl implements OrderQueueService {
    @Autowired
    private OrderQueueRepository orderQueueRepository;


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CuisineRepository cuisineRepository;

    @Autowired
    private SubCategoryRepository subcategoryRepository;


    public void processingOrderToQueue(Order newOrder, Long restId) {
        List<Category> cat = categoryRepository.getCategoryByRestaurantId(restId);
        for (OrderItem orderItem : newOrder.getMenuItemOrders()) {
            CustomerCartItems cc = new Gson().fromJson(orderItem.getCustomerCartItems(), CustomerCartItems.class);
            Product p = new Gson().fromJson(cc.getProductJson(), Product.class);
            OrderQueue orderQueue = new OrderQueue();
            orderQueue.setOrderItemId(orderItem.getOrderItemId());
            orderQueue.setStatus(orderItem.getStatus());
            orderQueue.setOrderCreationTime(orderItem.getOrderCreationTime());
            orderQueue.setQuantityOption(orderItem.getQuantityOption());
            orderQueue.setCategory(p.getCategory().getId());
            orderQueue.setSubCategory(p.getSubCategory().getId());
            orderQueue.setCuisine(p.getCuisine().getId());
            orderQueue.setImmediateStatus(orderItem.getImmediateStatus());

            if (orderItem.getImmediateStatus()) {
                if (orderQueueRepository.getImmediateStatusLastPriority(restId) != null) {
                    //orderQueue.setPriority(orderQueueRepository.getImmediateStatusLastPriority(restId) + 1);
                    OrderQueue orderQueue1 = orderQueueRepository.getImmediateLastOrder(restId);
                    orderQueueRepository.updateImmediateOrderQueuePriority(restId, orderQueue1.getPriority());
                    orderQueue.setPriority(orderQueueRepository.getImmediateStatusLastPriority(restId) + 1);
                } else {
                    orderQueue.setPriority(1);
                    if (orderQueueRepository.getWithoutImmediateLastOrder(restId) != null) {
                        OrderQueue orderQueue2 = orderQueueRepository.getWithoutImmediateLastOrder(restId);
                        orderQueueRepository.updateWithoutImmediateOrderQueuePriority(restId, orderQueue2.getQueueId());
                    }
                }

            } else {
                if (orderQueueRepository.getLastPriority(restId) != null) {
                    orderQueue.setPriority(orderQueueRepository.getLastPriority(restId) + 1);
                } else {
                    orderQueue.setPriority(1);
                }
                orderQueue.setCategory(p.getCategory().getId());
                orderQueue.setSubCategory(p.getSubCategory().getId());
                orderQueue.setCuisine(p.getCuisine().getId());
                if (orderQueueRepository.getLastPriority(restId) != null) {
                    orderQueue.setPriority(orderQueueRepository.getLastPriority(restId) + 1);
                } else {
                    orderQueue.setPriority(1);
                }
                orderQueue.setRestId(restId);
                orderQueueRepository.save(orderQueue);
            }
        }
    }

    public void updatingOrderToQueue (OrderItem orderItem, Long restId){
            OrderQueue orderQueue = orderQueueRepository.getOrderQueueByOrderItemId(restId, orderItem.getOrderItemId());
            orderQueueRepository.delete(orderQueue);
            orderQueueRepository.updateOrderQueuePriority(restId, orderQueue.getQueueId());

        }

    }

