package com.wityorestaurant.modules.config.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

@Entity
public class Staff {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	private RestaurantDetails retaurant;

	public Staff() {
	}

	public Staff(Long staffId, @NotBlank String staffName, @NotBlank String staffRole, @NotBlank String phoneNumber,
			RestaurantDetails retaurant) {
		this.staffId = staffId;
		this.staffName = staffName;
		this.staffRole = staffRole;
		this.phoneNumber = phoneNumber;
		this.retaurant = retaurant;
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

	public RestaurantDetails getRetaurant() {
		return retaurant;
	}

	public void setRetaurant(RestaurantDetails retaurant) {
		this.retaurant = retaurant;
	}
}
