package com.wityorestaurant.modules.config.repository;

import com.wityorestaurant.modules.config.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
