package com.paarr.repository;

import java.time.ZonedDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paarr.entity.ErrorLogModel;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLogModel, Integer> {

	Page<ErrorLogModel> findByOccurredtimeAfterAndMessageContainingIgnoreCaseOrderByOccurredtimeDesc(
			ZonedDateTime oneMonthAgo, String message, Pageable pageable);

}
