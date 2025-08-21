package com.paarr.entity;

import jakarta.persistence.*;
import lombok.*;

	@Entity
	@Table(name = "categories")
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public class CategoryModel {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "catagory_name")
	    private String categoryName;

	    private String description;
	    private String image;
	    private int sequence;
       
	    private Boolean active;
	}


