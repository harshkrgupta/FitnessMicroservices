package com.fitness.userservice.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
	

	private UserService userService;
	
	//get the user profile
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId){
		return ResponseEntity.ok(userService.getUserProfile(userId));
	}
	
	//validate the userid
	@GetMapping("/validate/{userId}")
	public ResponseEntity<Boolean> validateUserId(@PathVariable String userId){
		return ResponseEntity.ok(userService.validateUserId(userId));
	}
	
	//register the user
	@PostMapping()
	public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
		return ResponseEntity.ok(userService.register(request));
	}
	
	
	
}
