package com.wityorestaurant.modules.orderservice.dto;

import java.util.List;

import com.wityorestaurant.modules.orderservice.model.Order;

public class TableOrdersResponse {
	
	private List<Order> tableOrders;

	public List<Order> getTableOrders() {
		return tableOrders;
	}

	public void setTableOrders(List<Order> tableOrders) {
		this.tableOrders = tableOrders;
	}
}
