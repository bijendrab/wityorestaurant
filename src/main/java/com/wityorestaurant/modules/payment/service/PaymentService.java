package com.wityorestaurant.modules.payment.service;

import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.payment.dto.BillingDetailResponse;

public interface PaymentService {
	
	public BillingDetailResponse getOrderPaymentSummary(Long tableId, Long restId);

}
