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
	
	@Query(value="SELECT * FROM discount a WHERE a.discount_id=:discountId and a.restaurant_id=:restId", nativeQuery = true)
    Discount findRestaurantDiscountById(@Param("discountId") int discountId, @Param("restId") Long restId);

    @Query(value = "SELECT * FROM discount WHERE restaurant_id=?1", nativeQuery = true)
    List<Discount> findDiscountsByRestId(Long restId);

    @Query(value = "select * from discount dis " +
        "inner join discount_item disitem on disitem.discount_id = dis.discount_id " +
        "inner join discount_item_product disitemprod on disitemprod.discount_item_id=disitem.discount_item_id " +
        "inner join discount_item_product_quantity disitemprodq on disitemprodq.discount_item_id= disitem.discount_item_id " +
        "inner join item_quantity itemquant on itemquant.qoid=disitemprodq.qoid " +
        "where dis.restaurant_id=:restId and " +
        "disitemprod.product_id=:productId and " +
        "itemquant.qoption=:quantityOption",
        nativeQuery = true)
    Discount getDiscountByRestIdProductIdQuantityOption(@Param("restId") Long restId,
                                                        @Param("productId") String productId,
                                                        @Param("quantityOption") String quantityOption);


}
