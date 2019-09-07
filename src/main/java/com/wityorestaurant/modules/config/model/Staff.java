package com.wityorestaurant.modules.config.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

import java.io.Serializable;

@Entity
public class Staff  implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long staffId;
	@NotBlank
	private String staffName;
	@NotBlank
	private String staffRole;
	@NotBlank
	@Column(unique =  true, nullable = false)
	private String phoneNumber;
	@OneToOne
	@JoinColumn(name = "restaurant_id", nullable = false)
	@JsonIgnore
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
