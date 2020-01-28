package com.wityorestaurant.modules.menu.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wityorestaurant.modules.config.model.Category;
import com.wityorestaurant.modules.config.model.Cuisine;
import com.wityorestaurant.modules.config.model.SubCategory;
import com.wityorestaurant.modules.discount.model.DiscountItem;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import com.wityorestaurant.modules.tax.model.TaxProfile;

@Entity
@Table(name = "item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productId")
public class Product implements Serializable {

	private static final long serialVersionUID = -2186437972353058761L;

	@Id
    @Column(name = "productId")
    private String productId;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;
    
    @OneToOne
    @JoinColumn(name = "tax_profile_id")
    private TaxProfile appliedTax;
    
    @OneToOne
    @JoinColumn(name = "cuisine_id")
    private Cuisine cuisine;

    @Column(name = "description")
    private String description;

    @NotNull(message = "Product Name is mandatory")
    @Column(name = "name")
    private String productName;


    @Column(name = "isAdd")
    private Boolean isAdd;


    @Column(name = "isVeg")
    private Boolean isVeg;

    @Column(name = "isEnabled")
    private Boolean isEnabled;

    @Column(name = "prepTime")
    private int preparationTime;

    @Column(name = "selectedQuantity")
    private String selectedQuantity;


    @ManyToOne()
    @JoinColumn(name = "restId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "restId")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("restId")
    private RestaurantDetails restaurantDetails;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProductQuantityOptions> productQuantityOptions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "item_addOn",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private Set<AddOnProfile> addOnProfiles = new HashSet<>();

    public Product() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Boolean getIsVeg() {
        return this.isVeg;
    }

    public void setIsVeg(Boolean isVeg) {
        this.isVeg = isVeg;
    }

    public Boolean getIsEnabled() {
        return this.isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getDescription() {
        return this.description;
    }

    public String getProductName() {
        return this.productName;
    }

    public Boolean getIsAdd() {
        return this.isAdd;
    }

    public String getSelectedQuantity() {
        return this.selectedQuantity;
    }

    public Set<ProductQuantityOptions> getProductQuantityOptions() {
        return this.productQuantityOptions;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setIsAdd(Boolean isAdd) {
        this.isAdd = isAdd;
    }

    public void setSelectedQuantity(String selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public void setProductQuantityOptions(Set<ProductQuantityOptions> quantityOption) {
        this.productQuantityOptions = quantityOption;
    }


    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Cuisine getCuisine() {
		return cuisine;
	}

	public void setCuisine(Cuisine cuisine) {
		this.cuisine = cuisine;
	}

	public TaxProfile getAppliedTax() {
		return appliedTax;
	}

	public void setAppliedTax(TaxProfile appliedTax) {
		this.appliedTax = appliedTax;
	}

    public Set<AddOnProfile> getAddOnProfiles() {
        return addOnProfiles;
    }

    public void setAddOnProfiles(Set<AddOnProfile> addOnProfiles) {
        this.addOnProfiles = addOnProfiles;
    }
    
}
