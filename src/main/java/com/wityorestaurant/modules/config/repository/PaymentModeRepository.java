package com.wityorestaurant.modules.config.repository;

import javax.transaction.Transactional;
import java.util.List;
import com.wityorestaurant.modules.config.model.PaymentMode;
import com.wityorestaurant.modules.config.model.RestTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PaymentModeRepository extends JpaRepository<PaymentMode, Long> {
    @Query(value = "SELECT * from payment_mode where rest_id=?1", nativeQuery = true)
    List<PaymentMode> findPaymentByRestaurantId(Long restId);

    @Query(value = "SELECT * from payment_mode where payment_id=?1 AND rest_id=?2", nativeQuery = true)
    PaymentMode findPaymentByRestaurantIdAndPaymentId(Long paymentId, Long restId);

    @Transactional
    @Modifying
    @Query(value = "DELETE from payment_mode where payment_id=?1 AND rest_id=?2", nativeQuery = true)
    void deleteByRestaurantIdAndPaymentId(Long paymentId, Long restId);
}
