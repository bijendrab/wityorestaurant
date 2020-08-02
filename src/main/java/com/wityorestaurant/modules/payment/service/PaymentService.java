package com.wityorestaurant.modules.payment.service;

import com.wityorestaurant.modules.config.dto.PaymentModeDTO;
import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.orderservice.model.OrderHistory;
import com.wityorestaurant.modules.payment.dto.BillingDetailResponse;
import com.wityorestaurant.modules.payment.dto.PaymentUpdateDTO;

public interface PaymentService {
	
	BillingDetailResponse getOrderPaymentSummary(Long restId , Long tableId);
	Boolean updatePaymentStatus(Long restId , Long tableId, PaymentUpdateDTO paymentUpdateDTO);

}
