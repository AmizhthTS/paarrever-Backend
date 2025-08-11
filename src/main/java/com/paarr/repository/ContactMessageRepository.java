package com.paarr.repository;


import com.paarr.entity.ContactMessageModel;
import org.springframework.data.jpa.repository.JpaRepository;



	public interface ContactMessageRepository extends JpaRepository<ContactMessageModel, Long> {
	

}
