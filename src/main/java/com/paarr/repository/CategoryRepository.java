package com.paarr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.paarr.entity.CategoryModel;
@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
	    CategoryModel findByIdAndActive(Long id, Boolean active);
	    Page<CategoryModel> findByCategoryNameContainsIgnoreCaseAndActive(String categoryName, Boolean active, Pageable pageable);
	    Page<CategoryModel> findByActive(Boolean active, Pageable pageable);
		Page<CategoryModel> findByIdAndActive(long categoryId, boolean b, Pageable paging);
	
}
