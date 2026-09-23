package com.fitness.ActivityMicroservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserValidationService {
	private final WebClient userServiceWebClient;
	
	public Boolean validateUser(String userid) {
		try {
			return userServiceWebClient.get()
					.uri("/user/validate/{userid}",userid)
					.retrieve()
					.bodyToMono(Boolean.class)
					.block();
		}catch(WebClientResponseException E) {
			if(E.getStatusCode() == HttpStatus.NOT_FOUND)
				throw new RuntimeException("User not found: " +userid);
			if(E.getStatusCode() == HttpStatus.BAD_REQUEST)
				throw new RuntimeException("Invalid Request" + userid);
		}
		return false;
		
	}
}
