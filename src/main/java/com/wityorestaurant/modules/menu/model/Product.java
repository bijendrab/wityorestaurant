package com.wityorestaurant.modules.menu.model;

import com.fasterxml.jackson.annotation.*;
import com.wityorestaurant.modules.user.model.RestaurantDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "item")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="productId")
public class Product implements Serializable {
    @Id
    @Column(name = "productId")
    /*@GeneratedValue(strategy = GenerationType.AUTO)*/
    private String productId;

    /*@EmbeddedId
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty("productId")
    private ProductIdentity pId;*/

    @Column(name="category")
    private String category;

    @Column(name="subCategory")
    private String subCategory;

    @Column(name="cuisine")
    private String cuisine;

    @Column(name = "description")
    private String description;

    @NotNull(message = "Product Name is mandatory")
    @Column(name = "name")
    private String name;


    @Column(name = "isAdd")
    private Boolean isAdd;


    @Column(name = "isVeg")
    private Boolean isVeg;

    @Column(name = "isEnabled")
    private Boolean isEnabled;

    @Column(name = "prepTime")
    private int prepTime;

    @Column(name="selectedQuantity")
    private  String selectedQuantity;


    @ManyToOne()
    @JoinColumn(name="restId")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="restId")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty("restId")
    //@JsonIgnore
    private RestaurantDetails restaurantDetails;

    @OneToMany(mappedBy = "product",orphanRemoval = true,cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<ProductQuantityOptions> quantityOption;


    public Product() { }

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

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

   /* public Long getProductId() {
        return this.pId;
    }*/

    public String getCategory() {
        return this.category;
    }

    public String getSubCategory() {
        return this.subCategory;
    }

    public String getCuisine() {
        return this.cuisine;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getIsAdd() {
        return this.isAdd;
    }

    public String getSelectedQuantity() {
        return this.selectedQuantity;
    }

    public Set<ProductQuantityOptions> getQuantityOption() {
        return this.quantityOption;
    }

    /*public void setProductId(Long pId) {
        this.pId = pId;
    }*/

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsAdd(Boolean isAdd) {
        this.isAdd = isAdd;
    }

    public void setSelectedQuantity(String selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public void setQuantityOption(Set<ProductQuantityOptions> quantityOption) {
        this.quantityOption = quantityOption;
    }


    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }


}
