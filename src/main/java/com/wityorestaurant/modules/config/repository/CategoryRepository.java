package com.wityorestaurant.modules.config.repository;

import com.wityorestaurant.modules.config.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * from category where rest_id=?1", nativeQuery = true)
    public List<Category> getCategoryByRestaurantId(Long restId);
}
