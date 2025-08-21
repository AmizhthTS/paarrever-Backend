package com.paarr.repository;


import com.paarr.entity.ContactMessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



	public interface ContactMessageRepository extends JpaRepository<ContactMessageModel, Long> {
	
		Page<ContactMessageModel> findByFullNameContainingIgnoreCaseOrEmailAddressContainingIgnoreCase(
	            String fullName,
	            String emailAddress,
	            Pageable pageable
	    );

}
