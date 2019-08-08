package com.wityorestaurant.modules.reservation.repository;


import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Repository
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT a from Reservation a where a.customerInfo=:customerInfo")
    public Reservation getByCustomerId(@Param("customerInfo") String customerInfo);

    @Query(value = "SELECT * from reservation r inner join resttable c on c.id=r.table_Id where c.table_Number=:tableId", nativeQuery = true)
    public List<Reservation> getByTableId(@Param("tableId") Long tableId);
}
