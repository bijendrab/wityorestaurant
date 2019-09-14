package com.wityorestaurant.modules.config.repository;

import com.wityorestaurant.modules.config.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

	@Query(value = "select * from staff where staff.staff_id =?1 AND staff.rest_id=?2", nativeQuery =  true)
	public Staff findStaffByRestaurantId(Long staffId, Long restaurantId);
}
