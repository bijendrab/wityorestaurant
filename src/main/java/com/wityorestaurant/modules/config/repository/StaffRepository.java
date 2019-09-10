package com.wityorestaurant.modules.config.repository;

import com.wityorestaurant.modules.config.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

}
