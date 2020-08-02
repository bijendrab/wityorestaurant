package com.wityorestaurant.modules.menu.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "itemQuantity")
public class ProductQuantityOptions implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qoid")
    private int productQuantityOptionId;

    @Column(name = "qoption")
    private String quantityOption;

    @Column(name = "quantity")
    private String quantity;

    @NotNull(message = "Please provide some price")
    //@Min(value = 50, message = "Minimum value should be greater than 50")
    @Column(name = "price")
    private double price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "item_quantity_addOn", joinColumns = @JoinColumn(name = "qoid"), inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private Set<AddOnProfile> quantityOptionAddOnProfiles;

    public ProductQuantityOptions() {
    }

    public int getProductQuantityOptionId() {
        return productQuantityOptionId;
    }

    public void setProductQuantityOptionId(int QOId) {
        this.productQuantityOptionId = QOId;
    }

    public String getQuantityOption() {
        return quantityOption;
    }

    public void setQuantityOption(String option) {
        this.quantityOption = option;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Set<AddOnProfile> getQuantityOptionAddOnProfiles() {
        return quantityOptionAddOnProfiles;
    }

    public void setQuantityOptionAddOnProfiles(Set<AddOnProfile> quantityOptionAddOnProfiles) {
        this.quantityOptionAddOnProfiles = quantityOptionAddOnProfiles;
    }
}
