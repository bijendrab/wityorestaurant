package com.wityorestaurant.modules.discount.repository;

import javax.transaction.Transactional;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wityorestaurant.modules.discount.model.Discount;

@Repository
@Transactional
public interface DiscountRepository extends JpaRepository<Discount, Integer>{
	
	@Query(value="SELECT * FROM Discount a WHERE a.discount_id=:discountId and a.restaurant_id=:restId", nativeQuery = true)
    Discount findRestaurantDiscountById(@Param("discountId") int discountId, @Param("restId") Long restId);

    @Query(value = "SELECT * FROM Discount WHERE restaurant_id=?1", nativeQuery = true)
    List<Discount> findDiscountsByRestId(Long restId);
}
