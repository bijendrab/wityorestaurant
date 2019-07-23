package com.wityorestaurant.modules.menu.repository;

import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.model.ProductIdentity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface MenuRepository extends CrudRepository<Product, String> {
    //public Product findByName(String username);
    //Product findByProductId(Long productId);

    @Query("SELECT a FROM Product a WHERE a.productId=:productId and a.restaurantDetails.restId=:restId")
    Product findByItemAndRestId(@Param("productId") String productId, @Param("restId") Long restId);


}
