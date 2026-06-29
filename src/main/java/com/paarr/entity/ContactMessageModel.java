package com.paarr.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_messages")
@Data
public class ContactMessageModel {
	

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String fullName;

	    @Column(nullable = false)
	    private String emailAddress;

	    private String subject;
	    
	    private String phoneNumber;
	    @Column(name = "checkbox")
	    private Boolean checkbox;

	    @Column(nullable = false, columnDefinition = "TEXT")
	    private String message;

	    private LocalDateTime createdAt = LocalDateTime.now();
	

}
