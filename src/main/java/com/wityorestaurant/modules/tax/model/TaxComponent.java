package com.wityorestaurant.modules.tax.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TaxComponent implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long taxComponentId;
	private String componentName;
	private float weightage;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="tax_profile_id")
	private TaxProfile taxProfile;

	public TaxComponent() {
	}

	public Long getTaxComponentId() {
		return taxComponentId;
	}

	public void setTaxComponentId(Long taxComponentId) {
		this.taxComponentId = taxComponentId;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public float getWeightage() {
		return weightage;
	}

	public void setWeightage(float weightage) {
		this.weightage = weightage;
	}

	public TaxProfile getTaxProfile() {
		return taxProfile;
	}

	public void setTaxProfile(TaxProfile taxProfile) {
		this.taxProfile = taxProfile;
	}
}
