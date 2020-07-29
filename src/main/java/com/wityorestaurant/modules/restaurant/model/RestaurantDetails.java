package com.wityorestaurant.modules.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wityorestaurant.modules.cart.model.RestaurantCart;
import com.wityorestaurant.modules.config.model.*;
import com.wityorestaurant.modules.menu.model.AddOnProfile;
import com.wityorestaurant.modules.menu.model.Product;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "restId")
@Table(name = "restDetails")
public class RestaurantDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*@GenericGenerator(
            name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")*/
    private Long restId;
    private String restName;
    private String ownerName;
    private String phone;
    private String email;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String pin;
    private String bankAccountNumber;
    private String bankName;
    private String bankIfscCode;
    private String gstIn;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private RestaurantUser restaurantuser;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurantDetails", orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Category> categories;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurantDetails", orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<SubCategory> subCategories;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurantDetails", orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Cuisine> cuisines;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurantDetails", orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<QuantityOption> quantityOptions;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurantDetails", orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Product> product;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurantDetails", orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<AddOnProfile> addOnProfiles;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurantDetails", orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<RestTable> restTables;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private RestaurantCart cart;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurantDetails", orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Staff> staff;


    public RestaurantDetails() {
    }

    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankIfscCode() {
        return bankIfscCode;
    }

    public void setBankIfscCode(String bankIfscCode) {
        this.bankIfscCode = bankIfscCode;
    }

    public RestaurantUser getRestaurantuser() {
        return restaurantuser;
    }

    public void setRestaurantuser(RestaurantUser restaurantuser) {
        this.restaurantuser = restaurantuser;
    }

    public RestaurantUser getUser() {
        return restaurantuser;
    }

    public void setUser(RestaurantUser restaurantuser) {
        this.restaurantuser = restaurantuser;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Cuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }

    public List<QuantityOption> getQuantityOptions() {
        return quantityOptions;
    }

    public void setQuantityOptions(List<QuantityOption> quantityOptions) {
        this.quantityOptions = quantityOptions;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public List<AddOnProfile> getAddOnProfiles() {
        return addOnProfiles;
    }

    public void setAddOnProfiles(List<AddOnProfile> addOnProfiles) {
        this.addOnProfiles = addOnProfiles;
    }

    public List<RestTable> getRestTables() {
        return restTables;
    }

    public void setRestTables(List<RestTable> restTables) {
        this.restTables = restTables;
    }

    public RestaurantCart getCart() {
        return cart;
    }

    public void setCart(RestaurantCart cart) {
        this.cart = cart;
    }


    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public String getGstIn() {
        return gstIn;
    }

    public void setGstIn(String gstIn) {
        this.gstIn = gstIn;
    }
}
