package com.wityorestaurant.modules.orderservice.repository;

import java.util.List;
import com.wityorestaurant.modules.orderservice.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    @Query(value = "SELECT * from restaurant_order_history rof where rof.table_id=:tableId and rof.rest_id=:restId", nativeQuery = true)
    List<OrderHistory> getOrderTableHistory(@Param("tableId") Long tableId, @Param("restId") Long restId);

    @Query(value = "SELECT * from restaurant_order_history rof where rof.rest_id=:restId", nativeQuery = true)
    List<OrderHistory> getOrderRestaurantHistory( @Param("restId") Long restId);

    @Query(value = "SELECT * from restaurant_order_history rof where rof.order_history_id=:orderHistoryId and rof.table_id=:tableId and rof.table_id=:tableId and rof.rest_id=:restId", nativeQuery = true)
    OrderHistory getOrderByOrderHistoryId( @Param("tableId") Long tableId, @Param("restId") Long restId,
                                                 @Param("orderHistoryId") Long  orderHistoryId);
}
