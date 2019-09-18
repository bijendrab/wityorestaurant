package com.wityorestaurant.modules.tax.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wityorestaurant.modules.tax.model.TaxProfile;

@Repository
public interface TaxRepository extends JpaRepository<TaxProfile, Long>{
	
	
	@Query(value = "SELECT * FROM tax_profile WHERE tax_profile_id=?1 AND rest_id=?2", nativeQuery = true)
	TaxProfile findTaxProfileByRestId(Long taxProfileId, Long restId);
	
	@Query(value = "SELECT * FROM tax_profile WHERE rest_id=?1", nativeQuery = true)
	List<TaxProfile> findProfilesByRestId(Long restId);
	

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tax_profile WHERE tax_profile_id=?1", nativeQuery = true)
	void deleteTaxProfile(Long taxProfileId);
}
