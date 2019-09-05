package com.wityorestaurant.modules.config.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wityorestaurant.modules.config.model.RestTable;

@Repository
public interface RestTableRepository extends JpaRepository<RestTable, Long> {
    @Query(value = "SELECT * from resttable where rest_id=?1", nativeQuery = true)
    public List<RestTable> findByRestaurantId(Long restId);
    
    @Query(value = "SELECT * from resttable where id=?1 AND rest_id=?2", nativeQuery = true)
    public RestTable findByRestaurantIdAndTableId(Long id, Long restId);
}
