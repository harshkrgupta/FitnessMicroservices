package com.fitness.gateway.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;
	
	private String keycloakId;

	
	@NotBlank(message = "password is required")
	@Size(min = 6, message = "password must have atlease 6 characters")
	private String password;

	@NotBlank(message = "First name can not be empty")
	private String fname;
	private String lname;
	
	
}
