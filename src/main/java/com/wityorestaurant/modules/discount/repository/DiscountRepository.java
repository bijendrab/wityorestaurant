package com.wityorestaurant.modules.discount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wityorestaurant.modules.discount.model.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer>{

}
