package com.wityorestaurant.modules.config.repository;


import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.menu.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestTableRepository extends JpaRepository<RestTable, Long> {
    @Query(value = "SELECT * from resttable where rest_id=?1", nativeQuery = true)
    public List<RestTable> findByRestaurantId(Long restId);
}
