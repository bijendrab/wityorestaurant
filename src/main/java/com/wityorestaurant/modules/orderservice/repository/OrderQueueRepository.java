package com.wityorestaurant.modules.orderservice.repository;

import com.wityorestaurant.modules.orderservice.model.OrderQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OrderQueueRepository extends JpaRepository<OrderQueue, String> {
    @Query(value = "SELECT priority from orderqueue where rest_id=?1  ORDER BY queue_id DESC LIMIT 1", nativeQuery = true)
    Integer getLastPriority(Long restId);

    @Query(value = "SELECT priority from orderqueue where rest_id=?1 and immediate_status=true ORDER BY queue_id DESC LIMIT 1", nativeQuery = true)
    Integer getImmediateStatusLastPriority(Long restId);

    @Query(value = "SELECT * from orderqueue where rest_id=?1 and immediate_status=true ORDER BY priority DESC LIMIT 1", nativeQuery = true)
    OrderQueue getImmediateLastOrder(Long restId);

    @Query(value = "SELECT * from orderqueue where rest_id=?1 and immediate_status=false ORDER BY queue_id ASC LIMIT 1", nativeQuery = true)
    OrderQueue getWithoutImmediateLastOrder(Long restId);

    @Query(value = "SELECT * from orderqueue where rest_id=?1 and order_item_id=?2", nativeQuery = true)
    OrderQueue getOrderQueueByOrderItemId(Long restId, String orderItemId);

    @Modifying
    @Query(value = "UPDATE orderqueue SET priority=priority-1 where rest_id=?1 and queue_id > ?2", nativeQuery = true)
    int updateOrderQueuePriority(Long restId, Long orderQueueId);


    @Modifying
    @Query(value = "UPDATE orderqueue SET priority=priority+1 where rest_id=?1 and priority > ?2", nativeQuery = true)
    int updateImmediateOrderQueuePriority(Long restId,int priority);

    @Modifying
    @Query(value = "UPDATE orderqueue SET priority=priority+1 where rest_id=?1 and queue_id >= ?2", nativeQuery = true)
    int updateWithoutImmediateOrderQueuePriority(Long restId,Long orderQueueId);

}
