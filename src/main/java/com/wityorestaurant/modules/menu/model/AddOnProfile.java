package com.wityorestaurant.modules.menu.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

@Entity
@Table(name = "addOnProfile")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "profileId")
public class AddOnProfile implements Serializable {
    @Id
    @Column(name = "profileId")
    private String profileId;

    @NotNull(message = "AddOn Profile Name is mandatory")
    @Column(name = "profileName")
    private String profileName;

    @Column(name = "toggleAddOnItems")
    private Boolean toggleAddOnItems;

    @ManyToOne()
    @JoinColumn(name = "restId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "restId")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("restId")
    private RestaurantDetails restaurantDetails;

    @OneToMany(mappedBy = "addOnProfile", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AddOnItems> customItems;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }

    public Set<AddOnItems> getCustomItems() {
        return customItems;
    }

    public void setCustomItems(Set<AddOnItems> customItems) {
        this.customItems = customItems;
    }

    public Boolean getToggleAddOnItems() {
        return toggleAddOnItems;
    }

    public void setToggleAddOnItems(Boolean toggleAddOnItems) {
        this.toggleAddOnItems = toggleAddOnItems;
    }
}
