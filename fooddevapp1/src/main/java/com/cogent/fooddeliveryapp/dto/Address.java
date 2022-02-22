package com.cogent.fooddeliveryapp.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = {"addresses","roles"})

public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	 
	private Integer houseNumber;
	 	
	private String street;
	 		
	private String city;
	 		
	private String state;
	 		
	private String country;
	 		
	private Integer zip;
	
	@JsonIgnore
	@ManyToOne()
	private User user;
	

}
