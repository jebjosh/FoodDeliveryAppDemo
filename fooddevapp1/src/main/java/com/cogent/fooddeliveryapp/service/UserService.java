package com.cogent.fooddeliveryapp.service;

import java.util.List;
import java.util.Optional;

import com.cogent.fooddeliveryapp.dto.User;

public interface UserService {
	
	public User addUser(User user);
	public Optional<User> getUserById(Long id);
	public List<User> getAllUsers();
	public String  deleteUserById(Long id);
	public List<User> getAllUsersAscOrder();
	public List<User> getAllUsersDescOrder();
	public User updateUser(User user);
	public boolean existsById(Long id);
}
