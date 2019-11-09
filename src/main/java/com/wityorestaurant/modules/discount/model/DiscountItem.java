package com.wityorestaurant.modules.discount.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DiscountItem implements Serializable {

	private static final long serialVersionUID = 6121574713993131921L;

	@Id
	private Integer discountItemId;
	private LocalDate startDate;
	private LocalTime startTime;
	private boolean isEndingType;
	private LocalDate endDate;
	private LocalTime endTime;

	public Integer getDiscountItemId() {
		return discountItemId;
	}

	public void setDiscountItemId(Integer discountItemId) {
		this.discountItemId = discountItemId;
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

	public boolean isEndingType() {
		return isEndingType;
	}

	public void setEndingType(boolean isEndingType) {
		this.isEndingType = isEndingType;
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

}
