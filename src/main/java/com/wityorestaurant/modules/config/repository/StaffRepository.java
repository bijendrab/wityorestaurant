package com.wityorestaurant.modules.config.repository;

import com.wityorestaurant.modules.config.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

	@Query(value = "select * from staff where staff.staff_id =?1 AND staff.rest_id=?2", nativeQuery =  true)
	Staff findStaffByRestaurantId(Long staffId, Long restaurantId);

	@Transactional
	@Modifying
	@Query(value = "delete from staff where staff.staff_id =?1 AND staff.rest_id=?2", nativeQuery =  true)
	void deleteStaffByRestaurantId(Long staffId, Long restaurantId);
}
