package com.wityorestaurant.modules.menu.repository;

import javax.transaction.Transactional;
import java.util.List;
import com.wityorestaurant.modules.menu.model.AddOnProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface AddOnRepository extends JpaRepository<AddOnProfile, String> {

    @Query("SELECT a FROM AddOnProfile a WHERE a.profileId=:profileId and a.restaurantDetails.restId=:restId")
    AddOnProfile findAddOnProfileByProfileAndRestId(@Param("profileId") String productId, @Param("restId") Long restId);

    @Query(value = "SELECT * from addOnProfile where rest_id=?1", nativeQuery = true)
    List<AddOnProfile> findAddonProfilesByRestaurantId(Long restId);

}
