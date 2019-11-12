package com.wityorestaurant.modules.discount.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Entity;
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
	private String discountDescription;
	private String discountValueType;
	private float totalRupeesDiscount;
	private float totalPercentageDiscount;
	private String frequency;
	private LocalDate startDate;
	private LocalTime startTime;
	private String endOption;
	private LocalDate endDate;
	private LocalTime endTime;

	@OneToMany(mappedBy = "discount")
//	@Cascade(CascadeType.ALL)
//	@JoinTable(joinColumns = @JoinColumn(name = "discount_id"), inverseJoinColumns = @JoinColumn(name = "discount_item_id"))
	private List<DiscountItem> discountedItems;

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

	public float getTotalRupeesDiscount() {
		return totalRupeesDiscount;
	}

	public void setTotalRupeesDiscount(float totalRupeesDiscount) {
		this.totalRupeesDiscount = totalRupeesDiscount;
	}

	public float getTotalPercentageDiscount() {
		return totalPercentageDiscount;
	}

	public void setTotalPercentageDiscount(float totalPercentageDiscount) {
		this.totalPercentageDiscount = totalPercentageDiscount;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public List<DiscountItem> getDiscountedItems() {
		return discountedItems;
	}

	public void setDiscountedItems(List<DiscountItem> discountedItems) {
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

}
