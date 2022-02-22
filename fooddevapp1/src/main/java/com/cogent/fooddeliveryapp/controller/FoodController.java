package com.cogent.fooddeliveryapp.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.fooddeliveryapp.dto.Food;
import com.cogent.fooddeliveryapp.exceptions.NoDataFoundException;
import com.cogent.fooddeliveryapp.repository.FoodRepository;

@RestController
@RequestMapping("/food")

@Validated
public class FoodController {
	

	@Autowired
	FoodRepository foodRepository;
	
	@PostMapping(value="")
	public ResponseEntity<?>  addFodd(@Valid @RequestBody Food food){
		System.out.println("yessssss");
		Food food2 = foodRepository.save(food);
		System.out.println("yesss");
		return ResponseEntity.status(201).body(food2);
	}
	
	
	@GetMapping(value ="/{id}")
	public ResponseEntity<?> getFoodById(@PathVariable("id") @Min(1) Long id){
		
		Food food = foodRepository.findById(id).orElseThrow(()-> new NoDataFoundException("No food with that ID"));
		
		return ResponseEntity.ok(food);
	}
	@GetMapping(value ="")
	public ResponseEntity<?> getAllFood(){
		
		List<Food> allFood = foodRepository.findAll();
		
		if(allFood.size()==0) {
			throw new NoDataFoundException("no users found"); 
		}
		
		return ResponseEntity.ok(allFood);
	}
	
	@GetMapping(value ="/all/asc")
	public ResponseEntity<?> getAllFoodAscOrder(){
		
		List<Food> allFood = foodRepository.findAll();
		
		if(allFood.size()==0) {
			throw new NoDataFoundException("no users found"); 
		}
		
		Collections.sort(allFood, (a,b) -> a.getId().compareTo(b.getId()));
		return ResponseEntity.status(200).body(allFood);
	}
	@GetMapping(value ="/all/desc")
	public ResponseEntity<?> getAllFoodDescOrder(){
		
		List<Food> allFood = foodRepository.findAll();
		
		if(allFood.size()==0) {
			throw new NoDataFoundException("no users found"); 
		}
		
		Collections.sort(allFood, (a,b) -> b.getId().compareTo(a.getId()));
		return ResponseEntity.status(200).body(allFood);
	}
	
}
