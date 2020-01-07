package com.wityorestaurant.modules.discount.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "discountId")
public class Discount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int discountId;
	private String discountName;
	private String discountType;
	private String discountValueType;
	private String discountDescription;
	private float discountValue;
	private String frequency;
	private LocalDate startDate;
	private LocalTime startTime;
	private String endOption;
	private LocalDate endDate;
	private LocalTime endTime;
	private String daysOfMonth;
	private String daysOfWeek;
	private Boolean isEnabled;
	
	

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "discount", orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<DiscountItem> discountedItems;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private RestaurantDetails restaurant;

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getDiscountDescription() {
		return discountDescription;
	}

	public void setDiscountDescription(String discountDescription) {
		this.discountDescription = discountDescription;
	}

	public String getDiscountValueType() {
		return discountValueType;
	}

	public void setDiscountValueType(String discountValueType) {
		this.discountValueType = discountValueType;
	}

	

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Set<DiscountItem> getDiscountedItems() {
		return discountedItems;
	}

	public void setDiscountedItems(Set<DiscountItem> discountedItems) {
		this.discountedItems = discountedItems;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public String getEndOption() {
		return endOption;
	}

	public void setEndOption(String endOption) {
		this.endOption = endOption;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public RestaurantDetails getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantDetails restaurant) {
		this.restaurant = restaurant;
	}

	public String getDaysOfMonth() {
		return daysOfMonth;
	}

	public void setDaysOfMonth(String daysOfMonth) {
		this.daysOfMonth = daysOfMonth;
	}

	public float getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(float discountValue) {
		this.discountValue = discountValue;
	}

	public String getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(String daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	public Boolean getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
