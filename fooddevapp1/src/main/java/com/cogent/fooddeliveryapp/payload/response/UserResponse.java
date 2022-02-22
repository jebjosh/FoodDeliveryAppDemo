package com.cogent.fooddeliveryapp.payload.response;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.cogent.fooddeliveryapp.payload.request.AddressRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserResponse {

	@NotBlank
	private String email;

	private String name;
	
	private Set<AddressRequest> address;
	
	private LocalDate doj;
	
	private Set<String> roles;
}
