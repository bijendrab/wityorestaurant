package com.wityorestaurant.modules.orderservice.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CancelledOrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cancelledOrderItemId;
	private Boolean isCustomerOrder;
	private Boolean isRestaurantOrder;
	private Long customerId;
	private Long restaurantId;
	private Long orderId;
	private LocalDateTime cancellationTime;
	@ManyToOne
	@JoinColumn(name = "ordered_item_id")
	private OrderItem cancelledOrderItem;
	public Long getCancelledOrderItemId() {
		return cancelledOrderItemId;
	}
	public void setCancelledOrderItemId(Long cancelledOrderItemId) {
		this.cancelledOrderItemId = cancelledOrderItemId;
	}
	public Boolean getIsCustomerOrder() {
		return isCustomerOrder;
	}
	public void setIsCustomerOrder(Boolean isCustomerOrder) {
		this.isCustomerOrder = isCustomerOrder;
	}
	public Boolean getIsRestaurantOrder() {
		return isRestaurantOrder;
	}
	public void setIsRestaurantOrder(Boolean isRestaurantOrder) {
		this.isRestaurantOrder = isRestaurantOrder;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public LocalDateTime getCancellationTime() {
		return cancellationTime;
	}
	public void setCancellationTime(LocalDateTime cancellationTime) {
		this.cancellationTime = cancellationTime;
	}
	public OrderItem getCancelledOrderItem() {
		return cancelledOrderItem;
	}
	public void setCancelledOrderItem(OrderItem cancelledOrderItem) {
		this.cancelledOrderItem = cancelledOrderItem;
	}
}
