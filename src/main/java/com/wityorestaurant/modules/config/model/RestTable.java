package com.wityorestaurant.modules.config.model;

import com.fasterxml.jackson.annotation.*;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name = "restaurant_table")
public class RestTable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableId;

    @Column(name = "tableNumber")
    private Integer tableNumber;

    @Column(name = "tableSize")
    @Min(2)
    private Integer tableSize;

    @Column(name = "qrCode")
    private Integer qrCode;

    @ManyToOne
    @JoinColumn(name="restId")
    @JsonIgnore
    private RestaurantDetails restaurantDetails;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long id) {
        this.tableId = id;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getTableSize() {
        return tableSize;
    }

    public void setTableSize(Integer tableSize) {
        this.tableSize = tableSize;
    }

    public Integer getQrCode() {
        return qrCode;
    }

    public void setQrCode(Integer qrCode) {
        this.qrCode = qrCode;
    }

    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }
}
