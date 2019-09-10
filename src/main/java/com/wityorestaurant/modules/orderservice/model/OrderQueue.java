package com.wityorestaurant.modules.orderservice.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orderqueue")
public class OrderQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long QueueId;
    private String orderItemId;
    private String status;
    private Integer priority;
    private Long restId;
    private String category;
    private String subCategory;
    private String cuisine;
    private Date orderCreationTime;
    private String quantityOption;


    public Long getQueueId() {
        return QueueId;
    }

    public void setQueueId(Long queueId) {
        QueueId = queueId;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public Date getOrderCreationTime() {
        return orderCreationTime;
    }

    public void setOrderCreationTime(Date orderCreationTime) {
        this.orderCreationTime = orderCreationTime;
    }

    public String getQuantityOption() {
        return quantityOption;
    }

    public void setQuantityOption(String quantityOption) {
        this.quantityOption = quantityOption;
    }
}
