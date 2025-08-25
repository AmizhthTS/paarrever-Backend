package com.paarr.repository;

import com.paarr.entity.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepository extends JpaRepository<StoreModel, Long> {
//    StoreModel findByIdAndActive(Long id, Boolean active);
//    Page<StoreModel> findByStoreNameContainsIgnoreCaseAndActive(String name, Boolean active, Pageable pageable);
//    Page<StoreModel> findByAreaNameContainsIgnoreCaseAndActive(String areaName, Boolean active, Pageable pageable);
//	package com.paarr.repository;
//
//	import com.paarr.entity.StoreModel;
//	import org.springframework.data.jpa.repository.JpaRepository;
//	import org.springframework.data.domain.Page;
//	import org.springframework.data.domain.Pageable;
//
//	public interface StoreRepository extends JpaRepository<StoreModel, Long> {

	    StoreModel findByIdAndActive(Long id, Boolean active);

	    Page<StoreModel> findByStoreNameContainsIgnoreCaseAndActive(String name, Boolean active, Pageable pageable);

	    // ✅ Search by main area
	    Page<StoreModel> findByMainAreaContainsIgnoreCaseAndActive(String mainArea, Boolean active, Pageable pageable);

	    // ✅ Search by sub area
	    Page<StoreModel> findBySubAreaContainsIgnoreCaseAndActive(String subArea, Boolean active, Pageable pageable);

		Page<StoreModel> findByActive(boolean b, Pageable paging);
	

}
