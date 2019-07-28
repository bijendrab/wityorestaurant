package com.wityorestaurant.modules.menu.repository;

import com.wityorestaurant.modules.menu.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface MenuRepository extends CrudRepository<Product, String> {

    @Query("SELECT a FROM Product a WHERE a.productId=:productId and a.restaurantDetails.restId=:restId")
    Product findByItemAndRestId(@Param("productId") String productId, @Param("restId") Long restId);
    
    @Query(value = "SELECT * from item where rest_id=?1", nativeQuery = true)
    List<Product> findByRestaurantId(Long restId);


}
