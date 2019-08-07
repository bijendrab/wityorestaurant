package com.wityorestaurant.modules.config.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wityorestaurant.modules.config.model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

}
