package com.wityorestaurant.modules.menu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "addOnItems")
public class AddOnItems implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ItemId")
    private int ItemId;

    @NotNull(message = "AddOn Profile Name is mandatory")
    @Column(name = "itemName")
    private String itemName;

    @NotNull(message = "Please provide some price")
    //@Min(value = 50, message = "Minimum value should be greater than 50")
    @Column(name = "price")
    private double price;

    @Column(name = "isEnabled")
    private Boolean isEnabled;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "profileId")
    private AddOnProfile addOnProfile;

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Boolean getIsEnabled() {
        return this.isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public AddOnProfile getAddOnProfile() {
        return addOnProfile;
    }

    public void setAddOnProfile(AddOnProfile addOnProfile) {
        this.addOnProfile = addOnProfile;
    }
}
