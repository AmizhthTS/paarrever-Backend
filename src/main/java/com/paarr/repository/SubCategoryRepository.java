package com.paarr.repository;

import com.paarr.entity.SubCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategoryModel, Long> {
	    SubCategoryModel findByIdAndActive(Long id, Boolean active);
	    Page<SubCategoryModel> findByCategoryIdAndSubCategoryNameContainsIgnoreCaseAndActive(Long categoryId, String name, Boolean active, Pageable pageable);
	    Page<SubCategoryModel> findByCategoryIdAndActive(Long categoryId, Boolean active, Pageable pageable);
	    List<SubCategoryModel> findByCategoryIdAndActive(Long categoryId, Boolean active);
		Page<SubCategoryModel> findBySubCategoryNameContainsIgnoreCaseAndActive(String searchString, boolean b,
				Pageable paging);
		Page<SubCategoryModel> findByActive(boolean b, Pageable paging);
		
	

}
