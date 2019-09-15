package com.wityorestaurant.modules.tax.service;

import com.wityorestaurant.modules.tax.model.TaxProfile;

public interface TaxService {
	
	public TaxProfile addTaxProfile(TaxProfile profile);
	public TaxProfile editTaxProfile(TaxProfile taxProfile);
	public TaxProfile removeComponentsFromTaxProfile(Long taxProfileId, Long taxComponentId);
	public Boolean deleteTaxProfile(Long taxProfileId);

}
