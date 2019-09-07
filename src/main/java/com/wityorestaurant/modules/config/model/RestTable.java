package com.wityorestaurant.modules.config.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

@Entity
@Table(name = "resttable")
//@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class RestTable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tableNumber")
    private Integer tableNumber;

    @Column(name = "tableSize")
    @Min(2)
    private Integer tableSize;

    @Column(name = "qrCode")
    private Integer qrCode;

    @ManyToOne
    @JoinColumn(name="restId")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="restId")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty("restId")
    private RestaurantDetails restaurantDetails;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "relatedTable",orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<Reservation> reservationList;
    private boolean serviceChargeEnabled = false;
    private boolean packagingChargeEnabled = false;
    private boolean overAllDiscountEnabled = false;
    private float serviceCharge = 0.0F;
    private float packagingCharge = 0.0F;
    private float overallDiscount = 0.0F;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public List<Reservation> getReservationList() {
		return reservationList;
	}
	public void setReservationList(List<Reservation> reservationList) {
		this.reservationList = reservationList;
	}
	public boolean isServiceChargeEnabled() {
		return serviceChargeEnabled;
	}
	public void setServiceChargeEnabled(boolean serviceChargeEnabled) {
		this.serviceChargeEnabled = serviceChargeEnabled;
	}
	public boolean isPackagingChargeEnabled() {
		return packagingChargeEnabled;
	}
	public void setPackagingChargeEnabled(boolean packagingChargeEnabled) {
		this.packagingChargeEnabled = packagingChargeEnabled;
	}
	public boolean isOverAllDiscountEnabled() {
		return overAllDiscountEnabled;
	}
	public void setOverAllDiscountEnabled(boolean overAllDiscountEnabled) {
		this.overAllDiscountEnabled = overAllDiscountEnabled;
	}
	public float getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(float serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public float getPackagingCharge() {
		return packagingCharge;
	}
	public void setPackagingCharge(float packagingCharge) {
		this.packagingCharge = packagingCharge;
	}
	public float getOverallDiscount() {
		return overallDiscount;
	}
	public void setOverallDiscount(float overallDiscount) {
		this.overallDiscount = overallDiscount;
	}
  
}
