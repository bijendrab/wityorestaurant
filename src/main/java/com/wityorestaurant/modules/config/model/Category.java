package com.wityorestaurant.modules.config.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;
    private String categoryType;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
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
