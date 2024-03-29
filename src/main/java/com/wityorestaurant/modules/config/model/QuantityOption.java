package com.wityorestaurant.modules.config.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "quantityoption")
public class QuantityOption implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quantityOptionName;
    private String quantityOptionType;
    private int sequence;

    @ManyToOne
    @JoinColumn(name = "restId")
    private RestaurantDetails restaurantDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuantityOptionName() {
        return quantityOptionName;
    }

    public void setQuantityOptionName(String quantityOptionName) {
        this.quantityOptionName = quantityOptionName;
    }

    public String getQuantityOptionType() {
        return quantityOptionType;
    }

    public void setQuantityOptionType(String quantityOptionType) {
        this.quantityOptionType = quantityOptionType;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }
}
