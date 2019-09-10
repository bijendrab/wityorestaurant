package com.wityorestaurant.modules.orderservice.repository;

import com.wityorestaurant.modules.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * from foodorder fo inner join reservation r on fo.reservation_id=r.id inner join resttable c on c.id=r.table_Id where r.customer_Info=:customerInfo and c.rest_id=:restId", nativeQuery = true)
    Order getOrderByCustomer(@Param("customerInfo") String customerInfo, @Param("restId") Long restId);

    @Query(value = "SELECT * from foodorder fo inner join reservation r on fo.reservation_id=r.id inner join resttable c on c.id=r.table_Id where r.table_id=:tableId and c.rest_id=:restId", nativeQuery = true)
    List<Order> getOrderByTable(@Param("tableId") Long tableId, @Param("restId") Long restId);

    @Query(value = "SELECT * from foodorder fo inner join reservation r on fo.reservation_id=r.id inner join resttable c on c.id=r.table_Id where c.rest_id=:restId", nativeQuery = true)
    List<Order> getAllOrderByRestaurant(@Param("restId") Long restId);

}
