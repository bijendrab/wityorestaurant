package com.wityorestaurant.modules.restaurant.repository;

import com.wityorestaurant.modules.restaurant.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
}
