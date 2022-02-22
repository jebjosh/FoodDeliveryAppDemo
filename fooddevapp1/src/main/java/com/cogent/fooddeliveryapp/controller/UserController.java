package com.cogent.fooddeliveryapp.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.fooddeliveryapp.dto.Address;
import com.cogent.fooddeliveryapp.dto.Role;
import com.cogent.fooddeliveryapp.dto.User;
import com.cogent.fooddeliveryapp.enums.ERole;
import com.cogent.fooddeliveryapp.exceptions.IdNotFoundException;
import com.cogent.fooddeliveryapp.exceptions.NoDataFoundException;
import com.cogent.fooddeliveryapp.payload.request.AddressRequest;
import com.cogent.fooddeliveryapp.payload.request.SignupRequest;
import com.cogent.fooddeliveryapp.payload.response.UserResponse;
import com.cogent.fooddeliveryapp.repository.RoleRepository;
import com.cogent.fooddeliveryapp.service.UserService;

@RestController

@RequestMapping("/users")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signupRequest){
		
		Set<Role> roles = new HashSet<>();
		if(signupRequest.getRoles()==null) {
			Role userRole = roleRepo.findByRoleName(ERole.ROLE_USER)
					.orElseThrow(()->  new IdNotFoundException("RoleId not found exception"));
				roles.add(userRole);
		}
		else {
		signupRequest.getRoles().forEach(e->{
			
			switch (e) {
			case "user":
				
					Role userRole = roleRepo.findByRoleName(ERole.ROLE_USER)
					.orElseThrow(()->  new IdNotFoundException("RoleId not found exception"));
				roles.add(userRole);
				break;
			case "admin":
				Role userAdmin = roleRepo.findByRoleName(ERole.ROLE_ADMIN)
				.orElseThrow(()->  new IdNotFoundException("RoleId not found exception"));
			roles.add(userAdmin);

			default:
				break;
			} 
			
		});
		}
	User user = new User();
		
		Set<Address> ads = new HashSet<>();
		Set<Address> addresses = new HashSet<>();
		signupRequest.getAddresses().forEach(e->{
			Address address=  new Address();
			address.setCity(e.getCity());
			address.setCountry(e.getCountry());
			address.setHouseNumber(e.getHouseNumber());
			address.setState(e.getState());
			address.setStreet(e.getStreet());
			address.setUser(user);
			address.setZip(e.getZip());
			addresses.add(address);
		});
		
		user.setAddresses(addresses);
		
		user.setEmail(signupRequest.getEmail());
		user.setDoj(signupRequest.getDoj());
		user.setName(signupRequest.getName());
		user.setPassword(signupRequest.getPassword());
		user.setRoles(roles);
		
		
		
	
		
		User finalUser = userService.addUser(user);
		
		return ResponseEntity.status(201).body(finalUser);}
	
	@GetMapping()
	public ResponseEntity<?> getUserById() throws NoDataFoundException{
		return null;}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllUsers() throws NoDataFoundException{
		
		List <User> list = userService.getAllUsers();
		List<UserResponse> users = new ArrayList<>();
		list.forEach(e ->{
			UserResponse user = new UserResponse();
			user.setName(e.getName());
			user.setEmail(e.getEmail());
			user.setDoj(e.getDoj());
			Set<String> roles = new HashSet<>();
			e.getRoles().forEach(e2->{
				roles.add(e2.getRoleName().name());
			});
			
			Set<AddressRequest> addresses = new HashSet<>();
			AddressRequest address = new AddressRequest();
			e.getAddresses().forEach(e3 ->{
				address.setCity(e3.getCity());
				address.setCountry(e3.getCountry());
				address.setHouseNumber(e3.getHouseNumber());
				address.setState(e3.getState());
				address.setStreet(e3.getStreet());
				address.setZip(e3.getZip());
				addresses.add(address);
			});
			user.setRoles(roles);
			user.setAddress(addresses);
	users.add(user);
			
			
			
		});
		if (users.size()>0) {
			return ResponseEntity.ok(list);
		}else{
			
			throw new NoDataFoundException("no users found"); 
		}
		
	}
	
	
@DeleteMapping
public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) throws NoDataFoundException{
	
	if(userService.existsById(id)) {

		userService.deleteUserById(id);
		return ResponseEntity.noContent().build();
	}else {
		throw new NoDataFoundException("No user found with that Id");
	}
	}

@GetMapping(value = "/{id}")
public ResponseEntity<?> getUserById(@PathVariable("id")long id) throws NoDataFoundException {
	
User user =	userService.getUserById(id).orElseThrow(()->new NoDataFoundException("data not available"));
	// DTO ===> UserResponse()
UserResponse userResponse=  new UserResponse();
userResponse.setEmail(user.getEmail());
userResponse.setName(user.getName());
Set<String> roles= new HashSet<>();
userResponse.setDoj(user.getDoj());
user.getRoles().forEach(e2->{
	roles.add(e2.getRoleName().name());
});
Set<AddressRequest> addresses = new HashSet<>();
user.getAddresses().forEach(e3->{
	AddressRequest address2 = new AddressRequest();
	address2.setHouseNumber(e3.getHouseNumber());
	address2.setCity(e3.getCity());
	address2.setCountry(e3.getCountry());
	address2.setState(e3.getState());
	address2.setStreet(e3.getStreet());
	address2.setZip(e3.getZip());
	addresses.add(address2);
});
userResponse.setAddress(addresses);
userResponse.setRoles(roles);
return ResponseEntity.status(200).body(userResponse);
}
	
}
