package com.wityorestaurant.modules.config.repository;

import com.wityorestaurant.modules.config.model.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, Long> {
}
