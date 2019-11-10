package com.wityorestaurant.modules.discount.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "discountId")
public class Discount {

	@Id
	private int discountId;
	private String discountName;
	private String discountType;
	private String discountDescription;
	private boolean isRupeesType;
	private boolean totalRupeesDiscount;
	private boolean totalPercentageDiscount;
	private String frequency;

	@OneToMany
	@Cascade(CascadeType.ALL)
	private List<DiscountItem> discountItems;

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

	public boolean isRupeesType() {
		return isRupeesType;
	}

	public void setRupeesType(boolean isRupeesType) {
		this.isRupeesType = isRupeesType;
	}

	public boolean isTotalRupeesDiscount() {
		return totalRupeesDiscount;
	}

	public void setTotalRupeesDiscount(boolean totalRupeesDiscount) {
		this.totalRupeesDiscount = totalRupeesDiscount;
	}

	public boolean isTotalPercentageDiscount() {
		return totalPercentageDiscount;
	}

	public void setTotalPercentageDiscount(boolean totalPercentageDiscount) {
		this.totalPercentageDiscount = totalPercentageDiscount;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public List<DiscountItem> getDiscountItems() {
		return discountItems;
	}

	public void setDiscountItems(List<DiscountItem> discountItems) {
		this.discountItems = discountItems;
	}

}
