package com.wityorestaurant.modules.config.repository;

import com.wityorestaurant.modules.config.model.QuantityOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityOptionRepository extends JpaRepository<QuantityOption, Long> {
}
