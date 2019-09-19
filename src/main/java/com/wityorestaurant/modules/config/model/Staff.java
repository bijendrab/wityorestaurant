package com.wityorestaurant.modules.config.model;

import com.fasterxml.jackson.annotation.*;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
public class Staff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;
    @NotBlank
    private String staffName;
    @NotBlank
    private String staffRole;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "restId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "restId")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("restId")
    private RestaurantDetails restaurantDetails;

    public Staff() {
    }

    public Staff(Long staffId, @NotBlank String staffName, @NotBlank String staffRole, @NotBlank String phoneNumber,
                 RestaurantDetails restaurantDetails) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffRole = staffRole;
        this.phoneNumber = phoneNumber;
        this.restaurantDetails = restaurantDetails;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }
}
