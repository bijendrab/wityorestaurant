package com.wityorestaurant.modules.config.repository;


import com.wityorestaurant.modules.config.model.RestTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestTableRepository extends JpaRepository<RestTable, Long> {
}
