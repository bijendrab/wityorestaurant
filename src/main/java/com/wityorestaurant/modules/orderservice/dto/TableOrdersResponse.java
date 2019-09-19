package com.wityorestaurant.modules.orderservice.dto;

import com.wityorestaurant.modules.orderservice.model.Order;

import java.util.List;

public class TableOrdersResponse {

    private List<Order> tableOrders;

    public List<Order> getTableOrders() {
        return tableOrders;
    }

    public void setTableOrders(List<Order> tableOrders) {
        this.tableOrders = tableOrders;
    }
}
