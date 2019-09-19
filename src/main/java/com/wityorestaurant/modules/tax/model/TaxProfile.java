package com.wityorestaurant.modules.tax.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import com.fasterxml.jackson.annotation.*;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "taxProfileId")
public class TaxProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long taxProfileId;
	private String taxProfileName;
	private String taxType;
	private float taxPercent;
	private String appliedOn;

	@ManyToOne()
	@JoinColumn(name = "rest_id")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "restId")
	@JsonIdentityReference(alwaysAsId = true)
	@JsonProperty("restId")
	private RestaurantDetails restaurant;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "taxProfile", orphanRemoval = true)
	private List<TaxComponent> taxComponents;

	public Long getTaxProfileId() {
		return taxProfileId;
	}

	public void setTaxProfileId(Long taxProfileId) {
		this.taxProfileId = taxProfileId;
	}

	public String getTaxProfileName() {
		return taxProfileName;
	}

	public void setTaxProfileName(String taxProfileName) {
		this.taxProfileName = taxProfileName;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public float getTaxPercent() {
		return taxPercent;
	}

	public void setTaxPercent(float taxPercent) {
		this.taxPercent = taxPercent;
	}

	public String getAppliedOn() {
		return appliedOn;
	}

	public void setAppliedOn(String appliedOn) {
		this.appliedOn = appliedOn;
	}

	public RestaurantDetails getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantDetails restaurant) {
		this.restaurant = restaurant;
	}

	public List<TaxComponent> getTaxComponents() {
		return taxComponents;
	}

	public void setTaxComponents(List<TaxComponent> taxComponents) {
		this.taxComponents = taxComponents;
	}
	
}
