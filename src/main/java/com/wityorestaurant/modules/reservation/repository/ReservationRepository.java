package com.wityorestaurant.modules.reservation.repository;


import com.wityorestaurant.modules.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = "SELECT * from reservation r inner join resttable c on c.id=r.table_Id where r.customer_Info=:customerInfo and c.rest_id=:restId", nativeQuery = true)
    Reservation getByCustomerId(@Param("customerInfo") String customerInfo, @Param("restId") Long restId);

    @Query(value = "SELECT * from reservation r inner join resttable c on c.id=r.table_Id where c.table_Number=:tableId and c.rest_id=:restId", nativeQuery = true)
    List<Reservation> getByTableId(@Param("tableId") Long tableId, @Param("restId") Long restId);
}
