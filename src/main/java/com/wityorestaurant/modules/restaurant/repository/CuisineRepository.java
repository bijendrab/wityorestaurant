package com.wityorestaurant.modules.restaurant.repository;

import com.wityorestaurant.modules.restaurant.model.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, Long> {
}
