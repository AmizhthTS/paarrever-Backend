package com.paarr.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subcategories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryModel {


	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "subcategory_name", nullable = false)
	    private String subCategoryName;

	    private String description;

	    private Boolean active;

	    @ManyToOne
	    @JoinColumn(name = "category_id", nullable = false)
	    private CategoryModel category;
	

}
