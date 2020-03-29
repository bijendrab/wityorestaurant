package com.wityorestaurant.modules.orderservice.repository;

import java.util.List;
import com.wityorestaurant.modules.orderservice.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    @Query(value = "SELECT * from restaurant_order_history rof where rof.table_id=:tableId and rof.rest_id=:restId", nativeQuery = true)
    List<OrderHistory> getOrderHistory(@Param("tableId") Long tableId, @Param("restId") Long restId);
}
