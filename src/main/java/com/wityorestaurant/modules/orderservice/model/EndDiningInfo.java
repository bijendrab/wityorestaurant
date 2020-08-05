package com.wityorestaurant.modules.orderservice.model;

public class EndDiningInfo {

    private Long restId;
    private Long tableId;

    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }
}
