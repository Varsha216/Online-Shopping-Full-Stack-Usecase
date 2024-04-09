package com.varsha.salesorder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customerSOS")
public class Customer {
	
	@Id
	@Column(name = "customer_id")
	private Long id;
	@Column(name = "first_name", length = 10, nullable = false)
	private String firstName;
	@Column(name = "last_name", length = 10, nullable = false)
	private String lastName;
	@Column(name = "email", nullable = false)
	private String email;
}
