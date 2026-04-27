package com.paarr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.paarr.entity.FeedbackModel;

public interface FeedbackRepository extends JpaRepository<FeedbackModel, Long> {

	Page<FeedbackModel> findByActive(Boolean active, Pageable pageable);

	Page<FeedbackModel> findByIdAndActive(
	        long id,
	        boolean active,
	        Pageable pageable);

	Page<FeedbackModel> findByBranchNameContainingIgnoreCaseAndActive(
	        String branchName,
	        Boolean active,
	        Pageable pageable);

	Page<FeedbackModel> findByIdAndBranchNameContainingIgnoreCaseAndActive(
	        long id,
	        String branchName,
	        Boolean active,
	        Pageable pageable);

	FeedbackModel findByIdAndActive(Long id, boolean b);
}