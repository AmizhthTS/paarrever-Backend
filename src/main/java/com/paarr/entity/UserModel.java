package com.paarr.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {


	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(unique = true, nullable = false)
	    private String email; // decrypted & stored

	    @Column(nullable = false)
	    private String password; // BCrypt hashed

	    @Column
	    private String role = "USER"; // default role
	

}
