package com.wityorestaurant.modules.reservation.repository;

import javax.transaction.Transactional;
import com.wityorestaurant.modules.config.model.RestTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RestTableRepository  extends JpaRepository<RestTable, Long> {
    @Query(value = "SELECT *  from resttable r  where r.id=:tableId and c.rest_id=:restId", nativeQuery = true)
    RestTable getByTableId(@Param("tableId") Long tableId, @Param("restId") Long restId);
}
